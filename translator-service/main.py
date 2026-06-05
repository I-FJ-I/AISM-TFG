from fastapi import FastAPI, Depends, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from sqlalchemy.orm import Session
from database import engine, get_db, Base
from models import PersonOMOP

# This ensures the tables exist (in your case they already exist in OMOP, it won't do any harm)
Base.metadata.create_all(bind=engine)

app = FastAPI(title="OMOP to FHIR Facade", version="1.0")

# CORS permissions 
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
def translate_person_to_fhir(person: PersonOMOP) -> dict:
    # Basic mapping of OMOP gender vocabularies (8507=Male, 8532=Female)
    if person.gender_concept_id == 8507:
        fhir_gender = "male"
    elif person.gender_concept_id == 8532:
        fhir_gender = "female"
    else:
        fhir_gender = "unknown"

    # Build safe birth date (sometimes OMOP is missing month or day)
    year = person.year_of_birth or "1900"
    month = f"{person.month_of_birth:02d}" if person.month_of_birth else "01"
    day = f"{person.day_of_birth:02d}" if person.day_of_birth else "01"

    fhir_patient = {
        "resourceType": "Patient",
        "id": str(person.person_id),
        "gender": fhir_gender,
        "birthDate": f"{year}-{month}-{day}",
        "active": True, # FHIR permite indicar si el registro está activo
        "identifier": [
            {
                "system": "http://TFG/AISM",
                "value": person.person_source_value
            }
        ]
    }

    if person.race_concept_id:
        race_text = "Unknown"
        if person.race_concept_id == 8527:
            race_text = "White"
        elif person.race_concept_id == 8516:
            race_text = "Black or African American"
            
        fhir_patient["extension"] = [
            {
                "url": "http://hl7.org/fhir/us/core/StructureDefinition/us-core-race",
                "extension": [
                    {
                        "url": "text",
                        "valueString": race_text
                    }
                ]
            }
        ]

    return fhir_patient

# ---------------------------------------------------------
# ENDPOINTS
# ---------------------------------------------------------

@app.get("/fhir/Patient")
def get_patients(limit: int = 50, db: Session = Depends(get_db)):
    """Gets patients from OMOP and returns them as a FHIR Bundle"""
    # 1. We do the SELECT on OMOP
    omop_persons = db.query(PersonOMOP).limit(limit).all()
    
    # 2. We translate each row
    fhir_patients = [translate_person_to_fhir(p) for p in omop_persons]
    
    # 3. We package it as required by the FHIR standard (A Bundle)
    return {
        "resourceType": "Bundle",
        "type": "searchset",
        "total": len(fhir_patients),
        "entry": [{"resource": p} for p in fhir_patients]
    }

@app.get("/fhir/Patient/{patient_id}")
def get_patient_by_id(patient_id: int, db: Session = Depends(get_db)):
    """Gets a single patient by their ID"""
    person = db.query(PersonOMOP).filter(PersonOMOP.person_id == patient_id).first()
    
    if not person:
        raise HTTPException(status_code=404, detail="Patient not found in OMOP")
        
    return translate_person_to_fhir(person)