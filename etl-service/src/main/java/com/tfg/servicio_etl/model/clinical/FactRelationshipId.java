package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class FactRelationshipId implements Serializable {
    private Integer domainConceptId1;
    private Long factId1;
    private Integer domainConceptId2;
    private Long factId2;
    private Integer relationshipConceptId;
}
