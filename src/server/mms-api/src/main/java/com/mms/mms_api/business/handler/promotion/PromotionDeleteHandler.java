package com.mms.mms_api.business.handler.promotion;

import com.mms.mms_api.business.command.promotion.PromotionDeleteCommand;
import com.mms.mms_api.data.PromotionRepository;

public class PromotionDeleteHandler extends PromotionBaseHandler<PromotionDeleteCommand, Void> {

    public PromotionDeleteHandler(PromotionDeleteCommand request, PromotionRepository promotionRepository) {
        super(request, null, promotionRepository);
    }

    @Override
    public Void execute() {
        var promotion = promotionRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found"));
        promotionRepository.delete(promotion);
        return null;
    }
}