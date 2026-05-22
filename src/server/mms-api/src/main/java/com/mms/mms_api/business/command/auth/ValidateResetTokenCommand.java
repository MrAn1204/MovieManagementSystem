package com.mms.mms_api.business.command.auth;

import com.mms.mms_api.business.command.BaseCommand;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ValidateResetTokenCommand implements BaseCommand {
    @NotBlank(message = "{user.password.token.required}")
    private String token;
}
