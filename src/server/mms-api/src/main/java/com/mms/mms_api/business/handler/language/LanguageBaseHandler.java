package com.mms.mms_api.business.handler.language;

import com.mms.mms_api.business.handler.BaseHandler;

public abstract class LanguageBaseHandler<I, O> extends BaseHandler<I, O> {
    protected LanguageBaseHandler(I request) {
        super(request);
    }
}
