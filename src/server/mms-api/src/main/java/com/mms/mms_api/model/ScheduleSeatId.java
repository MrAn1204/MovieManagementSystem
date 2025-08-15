package com.mms.mms_api.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class ScheduleSeatId implements Serializable {
    private UUID scheduleId;
    private UUID seatId;
}
