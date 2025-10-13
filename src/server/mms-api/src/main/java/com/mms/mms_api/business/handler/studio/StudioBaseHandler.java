package com.mms.mms_api.business.handler.studio;

import com.mms.mms_api.business.handler.BaseHandler;

public abstract class StudioBaseHandler<I, O> extends BaseHandler<I, O> {
    protected StudioBaseHandler(I request) {
        super(request);
    }
}
