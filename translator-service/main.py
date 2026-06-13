from fastapi import FastAPI, Depends, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from sqlalchemy.orm import Session
from database import engine, get_db, Base
from models import PersonOMOP, ConditionOccurrenceOMOP, MeasurementOMOP, ObservationOMOP, DeathOMOP

Base.metadata.create_all(bind=engine)

app = FastAPI(title="OMOP to FHIR Facade", version="1.0")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# ---------------------------------------------------------
# TRANSLATION LOGIC (OMOP -> FHIR)
# ---------------------------------------------------------

def translate_person_to_fhir(person: PersonOMOP, death: DeathOMOP = None) -> dict:
    if person.gender_concept_id == 8507:
        fhir_gender = "male"
    elif person.gender_concept_id == 8532:
        fhir_gender = "female"
    else:
        fhir_gender = "unknown"

    year = person.year_of_birth or "1900"
    month = f"{person.month_of_birth:02d}" if person.month_of_birth else "01"
    day = f"{person.day_of_birth:02d}" if person.day_of_birth else "01"

    fhir_patient = {
        "resourceType": "Patient",
        "id": str(person.person_id),
        "gender": fhir_gender,
        "birthDate": f"{year}-{month}-{day}",
        "active": True if not death else False,
        "identifier": [
            {
                "system": "http://TFG/AISM",
                "value": person.person_source_value
            }
        ],
        "extension": []
    }

    if death and death.death_date:
        fhir_patient["deceasedDateTime"] = str(death.death_date)

    if hasattr(person, 'race_concept_id') and person.race_concept_id:
        race_map = {
            8527: {"code": "2106-3", "display": "White"},
            8516: {"code": "2054-5", "display": "Black or African American"},
            8515: {"code": "2028-9", "display": "Asian"},
            8657: {"code": "1002-5", "display": "American Indian or Alaska Native"},
            8557: {"code": "2076-8", "display": "Native Hawaiian or Other Pacific Islander"}
        }

        if person.race_concept_id in race_map:
            mapped_race = race_map[person.race_concept_id]
            fhir_patient["extension"].append({
                "url": "http://hl7.org/fhir/us/core/StructureDefinition/us-core-race",
                "extension": [
                    {
                        "url": "ombCategory",
                        "valueCoding": {
                            "system": "urn:oid:2.16.840.1.113883.6.238",
                            "code": mapped_race["code"],
                            "display": mapped_race["display"]
                        }
                    },
                    {
                        "url": "text",
                        "valueString": mapped_race["display"]
                    }
                ]
            })
        elif hasattr(person, 'race_source_value') and person.race_source_value:
             fhir_patient["extension"].append({
                "url": "http://hl7.org/fhir/us/core/StructureDefinition/us-core-race",
                "extension": [
                    {
                        "url": "text",
                        "valueString": person.race_source_value
                    }
                ]
            })

    if not fhir_patient["extension"]:
        del fhir_patient["extension"]

    return fhir_patient

def translate_condition_to_fhir(condition: ConditionOccurrenceOMOP) -> dict:
    fhir_condition = {
        "resourceType": "Condition",
        "id": str(condition.condition_occurrence_id),
        "subject": {
            "reference": f"Patient/{condition.person_id}"
        },
        "code": {
            "text": condition.condition_source_value
        }
    }
    
    if condition.condition_start_date:
        fhir_condition["onsetDateTime"] = str(condition.condition_start_date)
        
    return fhir_condition

def translate_measurement_to_fhir(measurement: MeasurementOMOP) -> dict:
    fhir_obs = {
        "resourceType": "Observation",
        "id": f"m-{measurement.measurement_id}",
        "status": "final",
        "subject": {
            "reference": f"Patient/{measurement.person_id}"
        },
        "code": {
            "text": measurement.measurement_source_value
        }
    }
    
    if measurement.measurement_date:
        fhir_obs["effectiveDateTime"] = str(measurement.measurement_date)
        
    if measurement.value_as_number is not None:
        fhir_obs["valueQuantity"] = {
            "value": measurement.value_as_number,
            "unit": measurement.unit_source_value or ""
        }
        
    return fhir_obs

def translate_observation_to_fhir(observation: ObservationOMOP) -> dict:
    fhir_obs = {
        "resourceType": "Observation",
        "id": f"o-{observation.observation_id}",
        "status": "final",
        "subject": {
            "reference": f"Patient/{observation.person_id}"
        },
        "code": {
            "text": observation.observation_source_value
        }
    }
    
    if observation.observation_date:
        fhir_obs["effectiveDateTime"] = str(observation.observation_date)
        
    return fhir_obs

# ---------------------------------------------------------
# ENDPOINTS
# ---------------------------------------------------------

@app.get("/fhir/Patient")
def get_patients(limit: int = 50, db: Session = Depends(get_db)):
    omop_persons = db.query(PersonOMOP).limit(limit).all()
    fhir_patients = []
    for p in omop_persons:
        death = db.query(DeathOMOP).filter(DeathOMOP.person_id == p.person_id).first()
        fhir_patients.append(translate_person_to_fhir(p, death))
        
    return {
        "resourceType": "Bundle",
        "type": "searchset",
        "total": len(fhir_patients),
        "entry": [{"resource": p} for p in fhir_patients]
    }

@app.get("/fhir/Condition")
def get_conditions(patient_id: int = None, limit: int = 100, db: Session = Depends(get_db)):
    query = db.query(ConditionOccurrenceOMOP)
    if patient_id:
        query = query.filter(ConditionOccurrenceOMOP.person_id == patient_id)
        
    omop_conditions = query.limit(limit).all()
    fhir_conditions = [translate_condition_to_fhir(c) for c in omop_conditions]
    
    return {
        "resourceType": "Bundle",
        "type": "searchset",
        "total": len(fhir_conditions),
        "entry": [{"resource": c} for c in fhir_conditions]
    }

@app.get("/fhir/Observation")
def get_observations(patient_id: int = None, limit: int = 100, db: Session = Depends(get_db)):
    """FHIR Observations combinan OMOP Measurements y OMOP Observations"""
    meas_query = db.query(MeasurementOMOP)
    obs_query = db.query(ObservationOMOP)
    
    if patient_id:
        meas_query = meas_query.filter(MeasurementOMOP.person_id == patient_id)
        obs_query = obs_query.filter(ObservationOMOP.person_id == patient_id)
        
    half_limit = limit // 2
    omop_measurements = meas_query.limit(half_limit).all()
    omop_observations = obs_query.limit(half_limit).all()
    
    fhir_resources = []
    fhir_resources.extend([translate_measurement_to_fhir(m) for m in omop_measurements])
    fhir_resources.extend([translate_observation_to_fhir(o) for o in omop_observations])
    
    return {
        "resourceType": "Bundle",
        "type": "searchset",
        "total": len(fhir_resources),
        "entry": [{"resource": r} for r in fhir_resources]
    }