package com.mms.mms_api.business.handler.promotion;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.dto.PromotionDto;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.util.mapper.PromotionMapper;

public class PromotionCreateHandler extends PromotionBaseHandler<PromotionCreateCommand, PromotionDto> {
    private final TicketRepository ticketRepository;

    public PromotionCreateHandler(PromotionCreateCommand request, PromotionMapper promotionMapper,
            PromotionRepository promotionRepository, TicketRepository ticketRepository) {
        super(request, promotionMapper, promotionRepository);
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public PromotionDto execute() {
        List<Ticket> tickets = ticketRepository.findByIdIn(request.getTicketIds());

        if (tickets.size() < request.getTicketIds().size()) {
            throw new InvalidInputException("promotion.tickets.invalid");
        }

        Promotion promotion = promotionMapper.toEntity(request);

        for (Ticket ticket : tickets) {
            ticket.setPromotion(promotion);
        }

        promotion.setTickets(tickets);

        Promotion savedPromotion = promotionRepository.save(promotion);

        return promotionMapper.toDto(savedPromotion);
    }
}