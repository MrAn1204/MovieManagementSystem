package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.auth.LoginCommand;
import com.mms.mms_api.business.service.AuthenticationService;
import com.mms.mms_api.dto.LoginResultDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationService authenticationService;
    
    @PostMapping("/login")
    public LoginResultDto login(@Valid @RequestBody LoginCommand request) {
        return authenticationService.handle(request);
    }
    
}
