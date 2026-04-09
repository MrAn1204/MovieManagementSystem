package com.mms.mms_api.business.handler.promotion;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.business.service.GscService;
import com.mms.mms_api.common.StoragePath;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.dto.promotion.PromotionDto;
import com.mms.mms_api.util.mapper.PromotionMapper;

@Component
public class PromotionCreateHandler extends PromotionBaseHandler<PromotionCreateCommand, PromotionDto> {
    private final GscService gscService;

    public PromotionCreateHandler(PromotionMapper promotionMapper,
            PromotionRepository promotionRepository, GscService gscService) {
        super(promotionMapper, promotionRepository);
        this.gscService = gscService;
    }

    @Override
    public PromotionDto execute(PromotionCreateCommand request) {
        String imageUrl = gscService.upload(request.getImage(), StoragePath.PROMOTION_IMAGE);

        Promotion promotion = promotionMapper.toEntity(request);
        promotion.setImage(imageUrl);

        Promotion savedPromotion = promotionRepository.save(promotion);

        return promotionMapper.toDto(savedPromotion);
    }
}