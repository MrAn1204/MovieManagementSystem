package com.mms.mms_api.business.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.auth.LoginCommand;
import com.mms.mms_api.business.command.auth.RegisterCommand;
import com.mms.mms_api.business.handler.auth.LoginCommandHandler;
import com.mms.mms_api.business.handler.auth.RegisterCommandHandler;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.LoginResultDto;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.util.JwtHelper;
import com.mms.mms_api.util.mapper.UserMapper;
import com.mms.mms_api.util.validator.UserValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final JwtHelper jwtHelper;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    
    private final UserValidator userValidator;

    public LoginResultDto handle(LoginCommand request) {
        LoginCommandHandler handler = new LoginCommandHandler(request, authenticationManager, jwtHelper);
        return handler.execute();
    }

    public UserDto handle(RegisterCommand request) {
        userValidator.validate(request);
        RegisterCommandHandler handler = new RegisterCommandHandler(request, userRepository, roleRepository, userMapper, passwordEncoder);
        return handler.execute();
    }
}
