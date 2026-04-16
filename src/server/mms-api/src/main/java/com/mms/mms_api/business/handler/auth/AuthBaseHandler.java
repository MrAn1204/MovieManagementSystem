package com.mms.mms_api.business.handler.auth;

import org.springframework.security.authentication.AuthenticationManager;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.util.JwtHelper;

/**
 * Base handler for authentication-related requests.
 */
public abstract class AuthBaseHandler<I, O> extends BaseHandler<I, O> {
    protected AuthenticationManager authenticationManager;

    protected JwtHelper jwtHelper;

    /**
     * Creates an authentication base handler.
     *
     * @param authenticationManager Spring Security authentication manager
     * @param jwtHelper JWT token utility
     */
    protected AuthBaseHandler(AuthenticationManager authenticationManager, JwtHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }
}
