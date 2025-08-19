package com.mms.mms_api.business.command.room;

import java.util.UUID;

import com.mms.mms_api.business.command.BaseDeleteCommand;

public class RoomDeleteCommand extends BaseDeleteCommand {
    public RoomDeleteCommand(UUID id) {
        super(id);
    }
}