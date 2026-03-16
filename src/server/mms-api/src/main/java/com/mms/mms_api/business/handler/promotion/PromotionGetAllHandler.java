package com.mms.mms_api.business.handler.promotion;

import com.mms.mms_api.business.query.promotion.PromotionGetAllQuery;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.dto.promotion.PromotionDto;
import com.mms.mms_api.util.mapper.PromotionMapper;
import java.util.List;

public class PromotionGetAllHandler extends PromotionBaseHandler<PromotionGetAllQuery, List<PromotionDto>> {

    public PromotionGetAllHandler(PromotionGetAllQuery request, PromotionMapper promotionMapper, PromotionRepository promotionRepository) {
        super(request, promotionMapper, promotionRepository);
    }

    @Override
    public List<PromotionDto> execute() {
        return promotionRepository.findAll().stream()
                .map(promotionMapper::toDto)
                .toList();
    }
}