package com.mms.mms_api.business.handler;

/**
 * Base abstraction for handling business-layer requests.
 *
 * @param <I> request/input type
 * @param <O> response/output type
 */
public abstract class BaseHandler<I, O> {
    /**
     * Handles a request and returns a response.
     *
     * @param request request object
     * @return handler result
     */
    public abstract O execute(I request);
}
