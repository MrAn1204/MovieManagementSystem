package com.mms.mms_api.business.handler.promotion;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.promotion.PromotionGetAllQuery;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.dto.promotion.PromotionDto;
import com.mms.mms_api.util.mapper.PromotionMapper;
import java.util.List;

@Component
public class PromotionGetAllHandler extends PromotionBaseHandler<PromotionGetAllQuery, List<PromotionDto>> {

    public PromotionGetAllHandler(PromotionMapper promotionMapper, PromotionRepository promotionRepository) {
        super(promotionMapper, promotionRepository);
    }

    @Override
    public List<PromotionDto> execute(PromotionGetAllQuery request) {
        return promotionRepository.findAll().stream()
                .map(promotionMapper::toDto)
                .toList();
    }
}