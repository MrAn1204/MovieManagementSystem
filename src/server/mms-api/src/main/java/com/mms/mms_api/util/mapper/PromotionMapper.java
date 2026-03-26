package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.business.command.promotion.PromotionUpdateCommand;
import com.mms.mms_api.business.service.GscService;
import com.mms.mms_api.dto.promotion.PromotionDto;
import com.mms.mms_api.model.Promotion;

@Mapper(config = DefaultMapperConfig.class)
public abstract class PromotionMapper {
    protected GscService gscService;

    @Autowired
    protected void setGscService(GscService gscService) {
        this.gscService = gscService;
    }

    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "discount", expression = "java(command.getDiscount() / 100.0)")
    public abstract Promotion toEntity(PromotionCreateCommand command);

    @Mapping(target = "image", qualifiedByName = "getImageUrl")
    public abstract PromotionDto toDto(Promotion promotion);

    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "discount", expression = "java(command.getDiscount() / 100.0)")
    public abstract void updateEntity(PromotionUpdateCommand command, @MappingTarget Promotion promotion);

    @Named("getImageUrl")
    protected String getImageUrl(String image) {
        if (image == null || image.isEmpty()) {
            return null;
        }

        return gscService.getPublicUrl(image);
    }
}
