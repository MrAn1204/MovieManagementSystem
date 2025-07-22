package com.mms.mms_api.business.handler;

public abstract class BaseHandler<I, O> {
    protected I request;
    
    protected BaseHandler(
        I request
    ) {
        this.request = request;
    }
    
    public abstract O execute();
}
