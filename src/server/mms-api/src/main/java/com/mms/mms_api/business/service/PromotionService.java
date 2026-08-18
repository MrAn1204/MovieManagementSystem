package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.business.command.promotion.PromotionDeleteCommand;
import com.mms.mms_api.business.command.promotion.PromotionUpdateCommand;
import com.mms.mms_api.business.query.promotion.PromotionGetAllQuery;
import com.mms.mms_api.business.query.promotion.PromotionGetByIdQuery;
import com.mms.mms_api.business.query.promotion.PromotionSearchQuery;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.promotion.PromotionDetailDto;
import com.mms.mms_api.dto.promotion.PromotionDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.PromotionValidator;

import lombok.AllArgsConstructor;

/**
 * Provides CRUD and search operations for promotions.
 */
@Service
@AllArgsConstructor
public class PromotionService {
    private final RequestMediator mediator;
    private final PromotionValidator promotionValidator;

    /**
     * Creates a promotion.
     *
     * @param request create command
     * @return created promotion DTO
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public PromotionDetailDto handle(PromotionCreateCommand request) {
        return mediator.execute(request);
    }

    /**
     * Returns all promotions.
     *
     * @param request get-all query
     * @return list of promotion DTOs
     */
    public List<PromotionDto> handle(PromotionGetAllQuery request) {
        return mediator.execute(request);
    }

    /**
     * Returns promotion details by id.
     *
     * @param request get-by-id query
     * @return promotion DTO
     */
    public PromotionDetailDto handle(PromotionGetByIdQuery request) {
        return mediator.execute(request);
    }

    /**
     * Updates a promotion.
     *
     * @param request update command
     * @return updated promotion DTO
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public PromotionDetailDto handle(PromotionUpdateCommand request) {
        promotionValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Searches promotions with pagination.
     *
     * @param request search query
     * @return paginated promotion result
     */
    public PaginatedResult<PromotionDto> handle(PromotionSearchQuery request) {
        return mediator.execute(request);
    }

    /**
     * Deletes a promotion.
     *
     * @param request delete command
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void handle(PromotionDeleteCommand request) {
        mediator.execute(request);
    }
}
