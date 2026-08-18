package com.mms.mms_api.business.handler.promotion;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.business.service.GscService;
import com.mms.mms_api.common.StoragePath;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.dto.promotion.PromotionDetailDto;
import com.mms.mms_api.util.mapper.PromotionMapper;

/**
 * Handles promotion creation commands.
 */
@Component
public class PromotionCreateHandler extends PromotionBaseHandler<PromotionCreateCommand, PromotionDetailDto> {
    private final GscService gscService;

    /**
     * Creates a PromotionCreateHandler.
     *
     * @param promotionMapper promotion mapper
     * @param promotionRepository promotion repository
     * @param gscService Google Cloud Storage service for image uploads
     */
    public PromotionCreateHandler(PromotionMapper promotionMapper,
            PromotionRepository promotionRepository, GscService gscService) {
        super(promotionMapper, promotionRepository);
        this.gscService = gscService;
    }

    /**
     * Creates a new promotion and uploads its image to cloud storage.
     *
     * @param request promotion create command
     * @return created promotion DTO
     */
    @Override
    public PromotionDetailDto execute(PromotionCreateCommand request) {
        String imageUrl = gscService.upload(request.getImage(), StoragePath.PROMOTION_IMAGE);

        Promotion promotion = promotionMapper.toEntity(request);
        promotion.setImage(imageUrl);

        Promotion savedPromotion = promotionRepository.save(promotion);

        return promotionMapper.toDetailDto(savedPromotion);
    }
}