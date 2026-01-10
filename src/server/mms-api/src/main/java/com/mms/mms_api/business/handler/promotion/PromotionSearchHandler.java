package com.mms.mms_api.business.handler.promotion;

import com.mms.mms_api.business.query.promotion.PromotionSearchQuery;
import com.mms.mms_api.business.specification.PromotionSpecification;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.dto.PromotionDto;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.util.SearchHelper;
import com.mms.mms_api.util.mapper.PromotionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PromotionSearchHandler extends PromotionBaseHandler<PromotionSearchQuery, PaginatedResult<PromotionDto>> {

    public PromotionSearchHandler(PromotionSearchQuery request, PromotionMapper promotionMapper,
            PromotionRepository promotionRepository) {
        super(request, promotionMapper, promotionRepository);
    }

    @Override
    public PaginatedResult<PromotionDto> execute() {
        Pageable pageable = SearchHelper.generatePageable(request.getSortDirection(), request.getSortBy(),
                request.getPageNumber(), request.getPageSize());

        Specification<Promotion> spec = new PromotionSpecification(request);

        Page<Promotion> promotions = promotionRepository.findAll(spec, pageable);

        List<PromotionDto> promotionDtos = promotions.getContent().stream()
                .map(promotionMapper::toDto)
                .toList();

        return new PaginatedResult<>(promotionDtos, promotions.getTotalElements(), promotions.getTotalPages(),
                request.getPageSize(), request.getPageNumber());
    }
}
