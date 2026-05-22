package com.mms.mms_api.business.command.auth;

import com.mms.mms_api.annotation.PasswordMatch;
import com.mms.mms_api.business.command.BaseCommand;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@PasswordMatch
public class PasswordResetCommand implements BaseCommand {
    @NotNull(message = "{user.password.token.required}")
    private String token;

    private String password;

    private String confirmPassword;
}
