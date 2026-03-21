package com.mms.mms_api.business.command.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginCommand {
    @NotNull(message = "{user.username.required}")
    private String username;

    @NotNull(message = "{user.password.required}")
    private String password;
}
