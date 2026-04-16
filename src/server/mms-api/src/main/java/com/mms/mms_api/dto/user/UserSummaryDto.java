package com.mms.mms_api.dto.user;

import java.util.UUID;

import lombok.Data;

/**
 * Lightweight DTO for embedding basic user identity data.
 *
 * Maps from {@link com.mms.mms_api.model.User User}.
 *
 * @see com.mms.mms_api.model.User User
 */
@Data
public class UserSummaryDto {
    private UUID id;

    private String username;

    private String phoneNumber;
}
