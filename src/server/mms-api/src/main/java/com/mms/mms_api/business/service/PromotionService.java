package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.business.handler.promotion.PromotionCreateHandler;
import com.mms.mms_api.business.handler.promotion.PromotionGetAllHandler;
import com.mms.mms_api.business.handler.promotion.PromotionGetByIdHandler;
import com.mms.mms_api.business.query.promotion.PromotionGetAllQuery;
import com.mms.mms_api.business.query.promotion.PromotionGetByIdQuery;
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
}
