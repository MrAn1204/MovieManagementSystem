package com.mms.mms_api.business.handler.promotion;

import com.mms.mms_api.business.query.promotion.PromotionGetByIdQuery;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.dto.promotion.PromotionDto;
import com.mms.mms_api.util.mapper.PromotionMapper;

public class PromotionGetByIdHandler extends PromotionBaseHandler<PromotionGetByIdQuery, PromotionDto> {

    public PromotionGetByIdHandler(PromotionGetByIdQuery request, PromotionMapper promotionMapper,
            PromotionRepository promotionRepository) {
        super(request, promotionMapper, promotionRepository);
    }

    @Override
    public PromotionDto execute() {
        Promotion promotion = promotionRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("promotion.notFound"));

        return promotionMapper.toDto(promotion);
    }
}