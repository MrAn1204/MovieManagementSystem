package com.mms.mms_api.business.service;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.auth.LoginCommand;
import com.mms.mms_api.business.command.auth.RegisterCommand;
import com.mms.mms_api.dto.auth.LoginResultDto;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.UserValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final RequestMediator mediator;
    
    private final UserValidator userValidator;

    public LoginResultDto handle(LoginCommand request) {
        return mediator.execute(request);
    }

    public UserDto handle(RegisterCommand request) {
        userValidator.validate(request);
        return mediator.execute(request);
    }
}
