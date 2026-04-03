package com.mms.mms_api.dto.user;

import java.util.UUID;

import lombok.Data;

@Data
public class UserSummaryDto {
    private UUID id;

    private String username;

    private String phoneNumber;
}
