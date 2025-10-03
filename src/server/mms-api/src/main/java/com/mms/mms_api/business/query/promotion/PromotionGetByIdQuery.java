package com.mms.mms_api.business.query.promotion;

import com.mms.mms_api.business.query.BaseGetByIdQuery;
import java.util.UUID;

public class PromotionGetByIdQuery extends BaseGetByIdQuery {
    public PromotionGetByIdQuery(UUID id) {
        super(id);
    }
}
