package com.mms.mms_api.business.command.schedule;

import java.util.UUID;

import com.mms.mms_api.business.command.BaseDeleteCommand;

/**
 * Command payload for deleting schedules.
 */
public class ScheduleDeleteCommand extends BaseDeleteCommand {

    public ScheduleDeleteCommand(UUID id) {
        super(id);
    }

}
