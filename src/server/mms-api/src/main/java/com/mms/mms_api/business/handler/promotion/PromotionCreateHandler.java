package com.mms.mms_api.business.handler.promotion;

import java.util.List;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.business.service.GscService;
import com.mms.mms_api.common.StoragePath;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.promotion.PromotionDto;
import com.mms.mms_api.util.mapper.PromotionMapper;

public class PromotionCreateHandler extends PromotionBaseHandler<PromotionCreateCommand, PromotionDto> {
    private final TicketRepository ticketRepository;

    private final GscService gscService;

    public PromotionCreateHandler(PromotionCreateCommand request, PromotionMapper promotionMapper,
            PromotionRepository promotionRepository, TicketRepository ticketRepository, GscService gscService) {
        super(request, promotionMapper, promotionRepository);
        this.ticketRepository = ticketRepository;
        this.gscService = gscService;
    }

    @Override
    public PromotionDto execute() {
        List<Ticket> tickets = ticketRepository.findByIdIn(request.getTicketIds());

        String imageUrl = gscService.upload(request.getImage(), StoragePath.PROMOTION_IMAGE);

        Promotion promotion = promotionMapper.toEntity(request);
        promotion.setImage(imageUrl);

        for (Ticket ticket : tickets) {
            ticket.setPromotion(promotion);
        }

        promotion.setTickets(tickets);

        Promotion savedPromotion = promotionRepository.save(promotion);

        return promotionMapper.toDto(savedPromotion);
    }
}