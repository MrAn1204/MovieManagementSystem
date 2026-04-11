package com.mms.mms_api.business.handler.promotion;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.promotion.PromotionGetByIdQuery;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.dto.promotion.PromotionDto;
import com.mms.mms_api.util.mapper.PromotionMapper;

/**
 * Handles requests to retrieve promotion by id.
 */
@Component
public class PromotionGetByIdHandler extends PromotionBaseHandler<PromotionGetByIdQuery, PromotionDto> {

    /**
     * Creates a PromotionGetByIdHandler.
     *
     * @param promotionMapper promotion mapper
     * @param promotionRepository promotion repository
     */
    public PromotionGetByIdHandler(PromotionMapper promotionMapper,
            PromotionRepository promotionRepository) {
        super(promotionMapper, promotionRepository);
    }

    /**
     * Retrieves a promotion by its identifier.
     *
     * @param request query containing the target promotion id
     * @return promotion DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the promotion does not exist
     */
    @Override
    public PromotionDto execute(PromotionGetByIdQuery request) {
        Promotion promotion = promotionRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("promotion.notFound"));

        return promotionMapper.toDto(promotion);
    }
}