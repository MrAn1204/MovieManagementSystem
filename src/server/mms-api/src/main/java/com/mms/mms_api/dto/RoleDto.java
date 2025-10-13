package com.mms.mms_api.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class RoleDto {
    private UUID id;

    private String name;

    private String description;
}
