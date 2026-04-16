package com.mms.mms_api.business.handler.promotion;

import org.springframework.stereotype.Component;


import com.mms.mms_api.business.command.promotion.PromotionDeleteCommand;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Ticket;

/**
 * Handles promotion delete commands.
 */
@Component
public class PromotionDeleteHandler extends PromotionBaseHandler<PromotionDeleteCommand, Void> {

    /**
     * Creates a PromotionDeleteHandler.
     *
     * @param promotionRepository promotion repository
     */
    public PromotionDeleteHandler(PromotionRepository promotionRepository) {
        super(null, promotionRepository);
    }

    /**
     * Deletes a promotion, detaches all associated tickets, and removes it from the database.
     *
     * @param request delete command containing the target promotion id
     * @return {@code null}
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the promotion does not exist
     */
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