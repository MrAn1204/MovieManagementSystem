package com.mms.mms_api.business.handler.promotion;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.util.mapper.PromotionMapper;

public abstract class PromotionBaseHandler<I, O> extends BaseHandler<I, O> {
    protected PromotionRepository promotionRepository;

    protected PromotionMapper promotionMapper;

    protected PromotionBaseHandler(I request, PromotionMapper promotionMapper,
            PromotionRepository promotionRepository) {
        super(request);
        this.promotionMapper = promotionMapper;
        this.promotionRepository = promotionRepository;
    }
}