package com.mms.mms_api.util.mapper;

import java.time.LocalDateTime;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.BaseCreateCommand;
import com.mms.mms_api.business.command.BaseUpdateCommand;
import com.mms.mms_api.model.BaseEntity;

/**
 * MapStruct hooks for maintaining audit timestamps on entities.
 */
@Mapper(componentModel = "spring")
public interface AuditMapperConfig {
    @AfterMapping
    default void setCreatedAt(BaseCreateCommand command, @MappingTarget BaseEntity entity) {
        entity.setCreatedAt(LocalDateTime.now());
    }

    @AfterMapping
    default void setUpdatedAt(BaseUpdateCommand command, @MappingTarget BaseEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
    }
}
