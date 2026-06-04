-- =================================================================================
-- Athena Vocabulary Data Loading Script for OMOP CDM v5.4 by Francisco Jose Gil Gil
-- =================================================================================

COPY cdm_v54.CONCEPT 
FROM '/vocabulary/CONCEPT.csv' 
WITH DELIMITER E'\t' CSV HEADER QUOTE E'\b';

COPY cdm_v54.VOCABULARY 
FROM '/vocabulary/VOCABULARY.csv' 
WITH DELIMITER E'\t' CSV HEADER QUOTE E'\b';

COPY cdm_v54.DOMAIN 
FROM '/vocabulary/DOMAIN.csv' 
WITH DELIMITER E'\t' CSV HEADER QUOTE E'\b';

COPY cdm_v54.CONCEPT_CLASS 
FROM '/vocabulary/CONCEPT_CLASS.csv' 
WITH DELIMITER E'\t' CSV HEADER QUOTE E'\b';

COPY cdm_v54.RELATIONSHIP 
FROM '/vocabulary/RELATIONSHIP.csv' 
WITH DELIMITER E'\t' CSV HEADER QUOTE E'\b';

COPY cdm_v54.CONCEPT_RELATIONSHIP 
FROM '/vocabulary/CONCEPT_RELATIONSHIP.csv' 
WITH DELIMITER E'\t' CSV HEADER QUOTE E'\b';

COPY cdm_v54.CONCEPT_ANCESTOR 
FROM '/vocabulary/CONCEPT_ANCESTOR.csv' 
WITH DELIMITER E'\t' CSV HEADER QUOTE E'\b';

COPY cdm_v54.CONCEPT_SYNONYM 
FROM '/vocabulary/CONCEPT_SYNONYM.csv' 
WITH DELIMITER E'\t' CSV HEADER QUOTE E'\b';

COPY cdm_v54.DRUG_STRENGTH 
FROM '/vocabulary/DRUG_STRENGTH.csv' 
WITH DELIMITER E'\t' CSV HEADER QUOTE E'\b';