package com.mms.mms_api.business.handler.promotion;

import org.springframework.stereotype.Component;


import com.mms.mms_api.business.command.promotion.PromotionDeleteCommand;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Ticket;

@Component
public class PromotionDeleteHandler extends PromotionBaseHandler<PromotionDeleteCommand, Void> {

    public PromotionDeleteHandler(PromotionRepository promotionRepository) {
        super(null, promotionRepository);
    }

    @Override
    public Void execute(PromotionDeleteCommand request) {
        Promotion promotion = promotionRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("promotion.notFound"));
        
        for (Ticket ticket : promotion.getTickets()) {
            ticket.setPromotion(null);
        }
        
        promotionRepository.delete(promotion);
        
        return null;
    }
}