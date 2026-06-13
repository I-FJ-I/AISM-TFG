from sqlalchemy import Column, Integer, String, Date, Float
from database import Base

class PersonOMOP(Base):
    __tablename__ = "person"
    __table_args__ = {'schema': 'cdm_v54'}

    person_id = Column(Integer, primary_key=True, index=True)
    gender_concept_id = Column(Integer)
    year_of_birth = Column(Integer)
    month_of_birth = Column(Integer)
    day_of_birth = Column(Integer)
    race_concept_id = Column(Integer)
    person_source_value = Column(String)
    location_id = Column(Integer)

class ConditionOccurrenceOMOP(Base):
    __tablename__ = "condition_occurrence"
    __table_args__ = {'schema': 'cdm_v54'}

    condition_occurrence_id = Column(Integer, primary_key=True, index=True)
    person_id = Column(Integer, index=True)
    condition_concept_id = Column(Integer)
    condition_start_date = Column(Date)
    condition_end_date = Column(Date)
    condition_source_value = Column(String)

class MeasurementOMOP(Base):
    __tablename__ = "measurement"
    __table_args__ = {'schema': 'cdm_v54'}

    measurement_id = Column(Integer, primary_key=True, index=True)
    person_id = Column(Integer, index=True)
    measurement_concept_id = Column(Integer)
    measurement_date = Column(Date)
    value_as_number = Column(Float)
    measurement_source_value = Column(String)
    unit_source_value = Column(String)

class ObservationOMOP(Base):
    __tablename__ = "observation"
    __table_args__ = {'schema': 'cdm_v54'}

    observation_id = Column(Integer, primary_key=True, index=True)
    person_id = Column(Integer, index=True)
    observation_concept_id = Column(Integer)
    observation_date = Column(Date)
    observation_source_value = Column(String)

class DeathOMOP(Base):
    __tablename__ = "death"
    __table_args__ = {'schema': 'cdm_v54'}

    person_id = Column(Integer, primary_key=True, index=True)
    death_date = Column(Date)