package com.mms.mms_api.business.handler.promotion;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.promotion.PromotionGetByIdQuery;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.dto.AuditDto;
import com.mms.mms_api.dto.promotion.PromotionDetailDto;
import com.mms.mms_api.util.CurrentUserHelper;
import com.mms.mms_api.util.mapper.PromotionMapper;

/**
 * Handles requests to retrieve promotion by id.
 */
@Component
public class PromotionGetByIdHandler extends PromotionBaseHandler<PromotionGetByIdQuery, PromotionDetailDto> {
    private final CurrentUserHelper currentUser; 

    /**
     * Creates a PromotionGetByIdHandler.
     *
     * @param promotionMapper promotion mapper
     * @param promotionRepository promotion repository
     * @param currentUser current user helper
     */
    public PromotionGetByIdHandler(
        PromotionMapper promotionMapper,
        PromotionRepository promotionRepository,
        CurrentUserHelper currentUser
    ) {
        super(promotionMapper, promotionRepository);
        this.currentUser = currentUser;
    }

    /**
     * Retrieves a promotion by its identifier.
     *
     * @param request query containing the target promotion id
     * @return promotion detail DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the promotion does not exist
     */
    @Override
    public PromotionDetailDto execute(PromotionGetByIdQuery request) {
        Promotion promotion = promotionRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("promotion.notFound"));

        PromotionDetailDto dto = promotionMapper.toDetailDto(promotion);

        if (currentUser.isAdmin()) {
            dto.setAudit(new AuditDto(promotion));
        }

        return dto;
    }
}