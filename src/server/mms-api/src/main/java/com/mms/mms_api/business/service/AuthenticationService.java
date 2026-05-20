package com.mms.mms_api.business.service;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.auth.ForgotPasswordCommand;
import com.mms.mms_api.business.command.auth.LoginCommand;
import com.mms.mms_api.business.command.auth.PasswordResetCommand;
import com.mms.mms_api.business.command.auth.RegisterCommand;
import com.mms.mms_api.dto.auth.LoginResultDto;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.UserValidator;

import lombok.AllArgsConstructor;

/**
 * Coordinates authentication and registration workflows.
 */
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final RequestMediator mediator;
    
    private final UserValidator userValidator;

    /**
     * Authenticates a user.
     *
     * @param request login command
     * @return login result DTO
     */
    public LoginResultDto handle(LoginCommand request) {
        return mediator.execute(request);
    }

    /**
     * Registers a new user.
     *
     * @param request register command
     * @return created user DTO
     */
    public UserDto handle(RegisterCommand request) {
        userValidator.validate(request);
        return mediator.execute(request);
    }

    public void handle(ForgotPasswordCommand request) {
        mediator.execute(request);
    }

    public void handle(PasswordResetCommand request) {
        mediator.execute(request);
    }
}
