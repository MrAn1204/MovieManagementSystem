package com.mms.mms_api.business.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.auth.LoginCommand;
import com.mms.mms_api.business.handler.auth.LoginCommandHandler;
import com.mms.mms_api.util.JwtHelper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private AuthenticationManager authenticationManager;

    private JwtHelper jwtHelper;

    public String handle(LoginCommand request) {
        LoginCommandHandler handler = new LoginCommandHandler(request, authenticationManager, jwtHelper);
        return handler.execute();
    }
}
