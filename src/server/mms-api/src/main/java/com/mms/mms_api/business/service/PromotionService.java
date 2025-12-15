package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.business.command.promotion.PromotionDeleteCommand;
import com.mms.mms_api.business.command.promotion.PromotionUpdateCommand;
import com.mms.mms_api.business.handler.promotion.PromotionCreateHandler;
import com.mms.mms_api.business.handler.promotion.PromotionDeleteHandler;
import com.mms.mms_api.business.handler.promotion.PromotionGetAllHandler;
import com.mms.mms_api.business.handler.promotion.PromotionGetByIdHandler;
import com.mms.mms_api.business.handler.promotion.PromotionSearchHandler;
import com.mms.mms_api.business.handler.promotion.PromotionUpdateHandler;
import com.mms.mms_api.business.query.promotion.PromotionGetAllQuery;
import com.mms.mms_api.business.query.promotion.PromotionGetByIdQuery;
import com.mms.mms_api.business.query.promotion.PromotionSearchQuery;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.PromotionDto;
import com.mms.mms_api.util.mapper.PromotionMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;

    private final TicketRepository ticketRepository;

    private final PromotionMapper promotionMapper;

    @Transactional
    public PromotionDto handle(PromotionCreateCommand request) {
        PromotionCreateHandler handler = new PromotionCreateHandler(request, promotionMapper, promotionRepository,
                ticketRepository);
        return handler.execute();
    }

    public List<PromotionDto> handle(PromotionGetAllQuery request) {
        PromotionGetAllHandler handler = new PromotionGetAllHandler(request, promotionMapper, promotionRepository);
        return handler.execute();
    }

    public PromotionDto handle(PromotionGetByIdQuery request) {
        PromotionGetByIdHandler handler = new PromotionGetByIdHandler(request, promotionMapper, promotionRepository);
        return handler.execute();
    }

    @Transactional
    public PromotionDto handle(PromotionUpdateCommand request) {
        PromotionUpdateHandler handler = new PromotionUpdateHandler(request, promotionMapper, promotionRepository,
                ticketRepository);
        return handler.execute();
    }

    public PaginatedResult<PromotionDto> handle(PromotionSearchQuery request) {
        PromotionSearchHandler handler = new PromotionSearchHandler(request, promotionMapper, promotionRepository);
        return handler.execute();
    }

    @Transactional
    public void handle(PromotionDeleteCommand request) {
        PromotionDeleteHandler handler = new PromotionDeleteHandler(request, promotionRepository);
        handler.execute();
    }
}
