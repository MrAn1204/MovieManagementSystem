package com.mms.mms_api.business.handler.role;

import com.mms.mms_api.business.handler.BaseHandler;

public abstract class RoleBaseHandler<I, O> extends BaseHandler<I, O> {
    protected RoleBaseHandler(I request) {
        super(request);
    }
}
