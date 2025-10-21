package com.mms.mms_api.business.handler.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mms.mms_api.business.command.auth.LoginCommand;
import com.mms.mms_api.security.UserInfo;
import com.mms.mms_api.util.JwtHelper;

public class LoginCommandHandler extends AuthBaseHandler<LoginCommand, String> {
    public LoginCommandHandler(LoginCommand request, AuthenticationManager authenticationManager, JwtHelper jwtHelper) {
        super(request, authenticationManager, jwtHelper);
    }

    @Override
    public String execute() {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        return jwtHelper.generateToken((UserInfo) authentication.getPrincipal());
    }

}
