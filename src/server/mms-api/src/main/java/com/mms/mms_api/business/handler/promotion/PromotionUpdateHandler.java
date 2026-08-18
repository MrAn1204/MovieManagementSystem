package com.mms.mms_api.business.handler.promotion;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.promotion.PromotionUpdateCommand;
import com.mms.mms_api.business.service.GscService;
import com.mms.mms_api.common.StoragePath;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.dto.promotion.PromotionDetailDto;
import com.mms.mms_api.util.mapper.PromotionMapper;

/**
 * Handles promotion update commands.
 */
@Component
public class PromotionUpdateHandler extends PromotionBaseHandler<PromotionUpdateCommand, PromotionDetailDto> {
    private final GscService gscService;

    /**
     * Creates a PromotionUpdateHandler.
     *
     * @param promotionMapper promotion mapper
     * @param promotionRepository promotion repository
     * @param gscService Google Cloud Storage service for image management
     */
    public PromotionUpdateHandler(PromotionMapper promotionMapper,
            PromotionRepository promotionRepository, GscService gscService) {
        super(promotionMapper, promotionRepository);
        this.gscService = gscService;
    }

    /**
     * Updates an existing promotion, replaces its stored image and removes the old one.
     *
     * @param request promotion update command
     * @return updated promotion DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the promotion does not exist
     */
    @Override
    public PromotionDetailDto execute(PromotionUpdateCommand request) {
        Promotion promotion = promotionRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("promotion.notFound"));

        String oldImageUrl = promotion.getImage();
        String newImageUrl = gscService.upload(request.getImage(), StoragePath.PROMOTION_IMAGE);

        promotionMapper.updateEntity(request, promotion);

        for (Ticket oldTicket : promotion.getTickets()) {
            oldTicket.setPromotion(null);
        }

        if (newImageUrl != null) {
            promotion.setImage(newImageUrl);
        }

        Promotion updatedPromotion = promotionRepository.save(promotion);

        if (newImageUrl != null && oldImageUrl != null) {
            gscService.delete(oldImageUrl);
        }

        return promotionMapper.toDetailDto(updatedPromotion);
    }
}