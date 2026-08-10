package com.mms.mms_api.dto;

import java.util.UUID;

import lombok.Data;

/**
 * Base DTO containing identifier field.
 *
 * @see com.mms.mms_api.model.BaseEntity BaseEntity
 */
@Data
public abstract class BaseDto {
    private UUID id;
}
