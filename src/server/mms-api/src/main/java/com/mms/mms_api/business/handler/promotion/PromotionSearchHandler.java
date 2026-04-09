package com.mms.mms_api.business.handler.promotion;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.promotion.PromotionSearchQuery;
import com.mms.mms_api.business.specification.PromotionSpecification;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.dto.promotion.PromotionDto;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.util.SearchHelper;
import com.mms.mms_api.util.mapper.PromotionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Component
public class PromotionSearchHandler extends PromotionBaseHandler<PromotionSearchQuery, PaginatedResult<PromotionDto>> {

    public PromotionSearchHandler(PromotionMapper promotionMapper,
            PromotionRepository promotionRepository) {
        super(promotionMapper, promotionRepository);
    }

    @Override
    public PaginatedResult<PromotionDto> execute(PromotionSearchQuery request) {
        Pageable pageable = SearchHelper.generatePageable(request.getPageNumber(), request.getPageSize());

        Specification<Promotion> spec = new PromotionSpecification(request);

        Page<Promotion> promotions = promotionRepository.findAll(spec, pageable);

        return SearchHelper.generatePaginatedResult(promotions, promotionMapper::toDto);
    }
}
