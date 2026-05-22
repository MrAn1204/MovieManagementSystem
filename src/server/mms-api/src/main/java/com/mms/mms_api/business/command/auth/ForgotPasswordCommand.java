package com.mms.mms_api.business.command.auth;

import com.mms.mms_api.business.command.BaseCommand;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForgotPasswordCommand implements BaseCommand {
    @NotNull(message = "{user.email.required}")
    @Email(message = "{user.email.invalid}")
    private String email;
}
