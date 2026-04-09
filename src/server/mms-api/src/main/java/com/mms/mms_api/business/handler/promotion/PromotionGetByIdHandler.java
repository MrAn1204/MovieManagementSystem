package com.mms.mms_api.business.handler.promotion;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.promotion.PromotionGetByIdQuery;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.dto.promotion.PromotionDto;
import com.mms.mms_api.util.mapper.PromotionMapper;

@Component
public class PromotionGetByIdHandler extends PromotionBaseHandler<PromotionGetByIdQuery, PromotionDto> {

    public PromotionGetByIdHandler(PromotionMapper promotionMapper,
            PromotionRepository promotionRepository) {
        super(promotionMapper, promotionRepository);
    }

    @Override
    public PromotionDto execute(PromotionGetByIdQuery request) {
        Promotion promotion = promotionRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("promotion.notFound"));

        return promotionMapper.toDto(promotion);
    }
}