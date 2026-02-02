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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private AuthenticationManager authenticationManager;

    private JwtHelper jwtHelper;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    public LoginResultDto handle(LoginCommand request) {
        LoginCommandHandler handler = new LoginCommandHandler(request, authenticationManager, jwtHelper);
        return handler.execute();
    }

    public UserDto handle(RegisterCommand request) {
        RegisterCommandHandler handler = new RegisterCommandHandler(request, userRepository, roleRepository, userMapper, passwordEncoder);
        return handler.execute();
    }
}
