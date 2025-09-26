package com.mms.mms_api.business.command.seat;

import java.util.UUID;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class SeatUpdateCommand extends BaseUpdateCommand {
    private int seatColumn;

    private int seatRow;

    private String seatType;

    @NotNull(message = "{seat.name.required}")
    private String name;

    private UUID roomId;
}
