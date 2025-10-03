package com.mms.mms_api.business.service;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.business.handler.promotion.PromotionCreateHandler;
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
}
