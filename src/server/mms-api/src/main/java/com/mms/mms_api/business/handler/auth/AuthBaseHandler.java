package com.mms.mms_api.business.handler.auth;

import org.springframework.security.authentication.AuthenticationManager;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.util.JwtHelper;

public abstract class AuthBaseHandler<I, O> extends BaseHandler<I, O> {
    protected AuthenticationManager authenticationManager;

    protected JwtHelper jwtHelper;

    protected AuthBaseHandler(AuthenticationManager authenticationManager, JwtHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }
}
