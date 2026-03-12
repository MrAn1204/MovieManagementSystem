package com.mms.mms_api.business.query.seat;

import java.util.UUID;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SeatGetAllWithStatusQuery {
    @NotNull(message = "{schedule.notFound}")
    @NonNull
    private UUID scheduleId;
}
