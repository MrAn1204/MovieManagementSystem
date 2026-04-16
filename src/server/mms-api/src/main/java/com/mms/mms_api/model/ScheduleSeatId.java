package com.mms.mms_api.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Composite identifier for a schedule-seat mapping.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSeatId implements Serializable {
    private UUID scheduleId;
    private UUID seatId;
}
