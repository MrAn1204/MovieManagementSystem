package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.business.command.promotion.PromotionUpdateCommand;
import com.mms.mms_api.dto.PromotionDto;
import com.mms.mms_api.model.Promotion;

@Mapper(config = DefaultMapperConfig.class)
public interface PromotionMapper {
    @Mapping(target = "tickets", ignore = true)
    Promotion toEntity(PromotionCreateCommand command);

    PromotionDto toDto(Promotion promotion);

    @Mapping(target = "tickets", ignore = true)
    void updateEntity(PromotionUpdateCommand command, @MappingTarget Promotion promotion);
}
