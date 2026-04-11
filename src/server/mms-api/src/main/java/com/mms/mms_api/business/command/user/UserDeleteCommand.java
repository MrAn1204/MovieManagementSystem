package com.mms.mms_api.business.command.user;

import java.util.UUID;

import com.mms.mms_api.business.command.BaseDeleteCommand;

/**
 * Command payload for deleting users.
 */
public class UserDeleteCommand extends BaseDeleteCommand {
    public UserDeleteCommand(UUID id) {
        super(id);
    }
}