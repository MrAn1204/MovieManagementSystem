package com.mms.mms_api.business.commands.user;

import java.util.UUID;

import com.mms.mms_api.business.commands.BaseDeleteCommand;

public class UserDeleteCommand extends BaseDeleteCommand {
    public UserDeleteCommand(UUID id) {
        super(id);
    }
}