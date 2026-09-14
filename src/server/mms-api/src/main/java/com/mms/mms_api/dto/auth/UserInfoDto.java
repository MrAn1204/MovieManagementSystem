package com.mms.mms_api.dto.auth;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.mms.mms_api.common.AppConstant;
import com.mms.mms_api.security.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDto {
    private UUID id;

    private String username;

    private String email;

    private List<String> roles;

    private Instant expiry;

    public UserInfoDto(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.username = userInfo.getUsername();
        this.email = userInfo.getEmail();
        this.roles = userInfo.getRoles();
        this.expiry = Instant.now().plusMillis(AppConstant.LOGIN_EXPIRY);
    }
}
