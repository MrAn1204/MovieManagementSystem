package com.mms.mms_api.business.handler;

public abstract class BaseHandler<I, O> {
    public abstract O execute(I request);
}
