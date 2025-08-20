package com.mms.mms_api.business.command.seat;

import java.util.UUID;

import com.mms.mms_api.business.command.BaseCreateCommand;
import com.mms.mms_api.model.SeatType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class SeatCreateCommand extends BaseCreateCommand {
    private int seatColumn;

    private int seatRow;

    private SeatType seatType;

    private String name;

    private UUID roomId;
}
