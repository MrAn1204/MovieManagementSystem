package com.mms.mms_api.business.command.auth;

import com.mms.mms_api.business.command.BaseCommand;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Command payload for login operations.
 */
@Data
public class LoginCommand implements BaseCommand {
    @NotNull(message = "{user.username.required}")
    private String username;

    @NotNull(message = "{user.password.required}")
    private String password;
}
