package com.mms.mms_api.util.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.ReportingPolicy;

import com.mms.mms_api.business.command.BaseCreateCommand;
import com.mms.mms_api.model.BaseEntity;

@MapperConfig(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG)
public interface DefaultMapperConfig {
    @Mapping(target = "id", ignore = true)
    BaseEntity toEntity(BaseCreateCommand command);
}
