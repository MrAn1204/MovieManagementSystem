package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.auth.ForgotPasswordCommand;
import com.mms.mms_api.business.command.auth.LoginCommand;
import com.mms.mms_api.business.command.auth.PasswordResetCommand;
import com.mms.mms_api.business.command.auth.RegisterCommand;
import com.mms.mms_api.business.command.auth.ValidateResetTokenCommand;
import com.mms.mms_api.business.service.AuthenticationService;
import com.mms.mms_api.common.AppConstant;
import com.mms.mms_api.dto.auth.LoginResultDto;
import com.mms.mms_api.dto.auth.UserInfoDto;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.security.UserInfo;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Handles authentication and account registration endpoints.
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationService authenticationService;
    
    /**
     * Authenticates a user and returns access credentials.
     *
     * @param request login payload
     * @return login result containing token and related metadata
     */
    @PostMapping("/login")
    public ResponseEntity<UserInfoDto> login(@Valid @RequestBody LoginCommand request) {
        LoginResultDto result = authenticationService.handle(request);

        ResponseCookie cookie = getLoginCookie(result.getToken(), AppConstant.LOGIN_EXPIRY);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(result.getUserInfo());
    }
 
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = getLoginCookie("", 0);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserInfoDto> getCurrentUser(@AuthenticationPrincipal UserInfo userInfo) {
        return ResponseEntity.ok(new UserInfoDto(userInfo));
    }

    /**
     * Registers a new user account.
     *
     * @param request registration payload
     * @return created user information
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterCommand request) {
        UserDto userDto = authenticationService.handle(request);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordCommand request) {
        authenticationService.handle(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reset-password")
    public ResponseEntity<Boolean> validateResetToken(@Valid ValidateResetTokenCommand request) {
        boolean isValid = authenticationService.handle(request);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody PasswordResetCommand request) {
        authenticationService.handle(request);
        return ResponseEntity.ok().build();
    }

    private ResponseCookie getLoginCookie(String token, long maxAge) {
        return ResponseCookie.from(AppConstant.LOGIN_COOKIE_NAME, token)
                .httpOnly(true)
                .path("/")
                .maxAge(maxAge)
                .sameSite("Strict")
                .build();
    }
}
