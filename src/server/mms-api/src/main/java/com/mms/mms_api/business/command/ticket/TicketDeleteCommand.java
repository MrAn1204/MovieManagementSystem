package com.mms.mms_api.business.command.ticket;

import java.util.UUID;

import com.mms.mms_api.business.command.BaseDeleteCommand;

/**
 * Command payload for deleting tickets.
 */
public class TicketDeleteCommand extends BaseDeleteCommand {
    public TicketDeleteCommand(UUID id) {
        super(id);
    }
}