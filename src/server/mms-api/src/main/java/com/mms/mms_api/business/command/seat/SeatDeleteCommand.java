package com.mms.mms_api.business.command.seat;

import java.util.UUID;

import com.mms.mms_api.business.command.BaseDeleteCommand;

public class SeatDeleteCommand extends BaseDeleteCommand {

    public SeatDeleteCommand(UUID id) {
        super(id);
    }

}
