package com.mms.mms_api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

/**
 * Base DTO containing common audit and identifier fields.
 *
 * @see com.mms.mms_api.model.BaseEntity BaseEntity
 */
@Data
public abstract class BaseDto {
    private UUID id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String updatedBy;
}
