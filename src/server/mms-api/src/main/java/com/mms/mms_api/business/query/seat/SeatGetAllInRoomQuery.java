package com.mms.mms_api.business.query.seat;

import java.util.UUID;

import org.springframework.lang.NonNull;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SeatGetAllInRoomQuery {
    @NotNull(message = "{seat.room.required}")
    @NonNull
    private UUID roomId;
}
