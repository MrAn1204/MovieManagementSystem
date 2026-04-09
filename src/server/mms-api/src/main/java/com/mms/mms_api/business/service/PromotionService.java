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
import com.mms.mms_api.dto.promotion.PromotionDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.PromotionValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PromotionService {
    private final RequestMediator mediator;
    private final PromotionValidator promotionValidator;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public PromotionDto handle(PromotionCreateCommand request) {
        return mediator.execute(request);
    }

    public List<PromotionDto> handle(PromotionGetAllQuery request) {
        return mediator.execute(request);
    }

    public PromotionDto handle(PromotionGetByIdQuery request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public PromotionDto handle(PromotionUpdateCommand request) {
        promotionValidator.validate(request);
        return mediator.execute(request);
    }

    public PaginatedResult<PromotionDto> handle(PromotionSearchQuery request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void handle(PromotionDeleteCommand request) {
        mediator.execute(request);
    }
}
