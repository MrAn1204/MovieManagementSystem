package com.mms.mms_api.common;

import java.util.UUID;

import lombok.Data;

/**
 * Lightweight DTO for resources represented by an identifier and display name.
 */
@Data
public class IdNameDto {
    private UUID id;

    private String name;
}
