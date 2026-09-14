package com.mms.mms_api.business.handler.auth;

import org.springframework.stereotype.Component;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mms.mms_api.business.command.auth.LoginCommand;
import com.mms.mms_api.dto.auth.LoginResultDto;
import com.mms.mms_api.dto.auth.UserInfoDto;
import com.mms.mms_api.security.UserInfo;
import com.mms.mms_api.util.JwtHelper;

/**
 * Handles login commands.
 */
@Component
public class LoginCommandHandler extends AuthBaseHandler<LoginCommand, LoginResultDto> {
    /**
     * Creates a LoginCommandHandler.
     *
     * @param authenticationManager Spring Security authentication manager
     * @param jwtHelper JWT token utility
     */
    public LoginCommandHandler(AuthenticationManager authenticationManager, JwtHelper jwtHelper) {
        super(authenticationManager, jwtHelper);
    }

    /**
     * Authenticates the user credentials and returns a JWT token.
     *
     * @param request login command containing username and password
     * @return login result containing the issued JWT token
     */
    @Override
    public LoginResultDto execute(LoginCommand request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserInfo user = (UserInfo) authentication.getPrincipal();

        String token = jwtHelper.generateToken(user);

        UserInfoDto userInfo = new UserInfoDto(user);

        return new LoginResultDto(token, userInfo);
    }

}
