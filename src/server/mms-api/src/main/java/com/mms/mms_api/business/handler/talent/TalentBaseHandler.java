package com.mms.mms_api.business.handler.talent;

import com.mms.mms_api.business.handler.BaseHandler;

public abstract class TalentBaseHandler<I, O> extends BaseHandler<I, O> {
    protected TalentBaseHandler(I request) {
        super(request);
    }
}
