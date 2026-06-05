from sqlalchemy import Column, Integer, String
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
    
    # In OMOP, an original ID or name is sometimes stored in this column
    person_source_value = Column(String)