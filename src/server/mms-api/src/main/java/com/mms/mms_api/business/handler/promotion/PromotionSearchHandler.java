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
import org.springframework.data.jpa.domain.Specification;

/**
 * Handles promotion search queries.
 */
@Component
public class PromotionSearchHandler extends PromotionBaseHandler<PromotionSearchQuery, PaginatedResult<PromotionDto>> {

    /**
     * Creates a PromotionSearchHandler.
     *
     * @param promotionMapper promotion mapper
     * @param promotionRepository promotion repository
     */
    public PromotionSearchHandler(PromotionMapper promotionMapper,
            PromotionRepository promotionRepository) {
        super(promotionMapper, promotionRepository);
    }

    /**
     * Executes a paginated promotion search using the supplied filters.
     *
     * @param request search query with filters and pagination parameters
     * @return paginated result of promotion DTOs
     */
    @Override
    public PaginatedResult<PromotionDto> execute(PromotionSearchQuery request) {
        Specification<Promotion> spec = new PromotionSpecification(request);

        Page<Promotion> promotions = SearchHelper.getPage(request.getPageNumber(), request.getPageSize(),
                pageable -> promotionRepository.findAll(spec, pageable));

        return SearchHelper.getResult(promotions, promotionMapper::toDto);
    }
}
