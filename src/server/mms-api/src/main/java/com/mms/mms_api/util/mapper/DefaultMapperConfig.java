package com.mms.mms_api.util.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.mms.mms_api.business.command.BaseCreateCommand;
import com.mms.mms_api.business.command.BaseUpdateCommand;
import com.mms.mms_api.model.BaseEntity;

/**
 * Shared MapStruct configuration for create and update mappings.
 */
@MapperConfig(componentModel = "spring", uses = { AuditMapperConfig.class },
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG)
public interface DefaultMapperConfig {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BaseEntity toEntity(BaseCreateCommand command);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(BaseUpdateCommand command, @MappingTarget BaseEntity entity);
}
