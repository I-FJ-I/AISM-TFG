package com.tfg.servicio_etl.model.clinical;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * OMOP CDM v5.4 - DEVICE_EXPOSURE table
 *
 * Captures information about a person's exposure to a foreign physical
 * object or instrument that which is used for diagnostic or therapeutic
 * purposes through a mechanism beyond chemical action.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DeviceExposure {

    private Long deviceExposureId;

    private Long personId;

    /** FK to standard device concept. */
    private Integer deviceConceptId;

    private LocalDate deviceExposureStartDate;

    private LocalDateTime deviceExposureStartDatetime;

    private LocalDate deviceExposureEndDate;

    private LocalDateTime deviceExposureEndDatetime;

    /** FK indicating how the device exposure was recorded. */
    private Integer deviceTypeConceptId;

    private String uniqueDeviceId;

    private String productionId;

    private Integer quantity;

    private Long providerId;

    private Long visitOccurrenceId;

    private Long visitDetailId;

    private String deviceSourceValue;

    private Integer deviceSourceConceptId;

    private Integer unitConceptId;

    private String unitSourceValue;

    private Integer unitSourceConceptId;
}
