package com.mms.mms_api.business.handler.promotion;

import com.mms.mms_api.business.command.promotion.PromotionUpdateCommand;
import com.mms.mms_api.business.service.GscService;
import com.mms.mms_api.common.StoragePath;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.dto.promotion.PromotionDto;
import com.mms.mms_api.util.mapper.PromotionMapper;

public class PromotionUpdateHandler extends PromotionBaseHandler<PromotionUpdateCommand, PromotionDto> {
    private final GscService gscService;

    public PromotionUpdateHandler(PromotionUpdateCommand request, PromotionMapper promotionMapper,
            PromotionRepository promotionRepository, GscService gscService) {
        super(request, promotionMapper, promotionRepository);
        this.gscService = gscService;
    }

    @Override
    public PromotionDto execute() {
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

        return promotionMapper.toDto(updatedPromotion);
    }
}