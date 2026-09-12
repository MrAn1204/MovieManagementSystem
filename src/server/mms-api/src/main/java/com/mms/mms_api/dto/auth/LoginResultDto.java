package com.mms.mms_api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO returned after successful authentication.
 *
 * Contains JWT token for API access.
 */
@Data
@AllArgsConstructor
public class LoginResultDto {
    private String token;

    private UserInfoDto userInfo;
}
