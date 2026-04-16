package com.mms.mms_api.business.handler.promotion;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.util.mapper.PromotionMapper;

/**
 * Base handler for promotion-related requests.
 */
public abstract class PromotionBaseHandler<I, O> extends BaseHandler<I, O> {
    protected PromotionRepository promotionRepository;

    protected PromotionMapper promotionMapper;

    /**
     * Creates a promotion base handler.
     *
     * @param promotionMapper promotion mapper
     * @param promotionRepository promotion repository
     */
    protected PromotionBaseHandler(PromotionMapper promotionMapper,
            PromotionRepository promotionRepository) {
        this.promotionMapper = promotionMapper;
        this.promotionRepository = promotionRepository;
    }
}