package com.mms.mms_api.business.commands.user;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDeleteCommand {
    
    private final UUID id;
}