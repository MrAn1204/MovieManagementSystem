package com.mms.mms_api.mediator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.handler.BaseHandler;

/**
 * Mediates request execution by routing each request type to its handler.
 */
@Component
public class RequestMediator {
    private final Map<Class<?>, BaseHandler<?, ?>> handlerCache = new HashMap<>();

    /**
     * Builds a request-type to handler cache from Spring context.
     *
     * @param context Spring application context
     */
    public RequestMediator(ApplicationContext context) {
        @SuppressWarnings("rawtypes")
        Map<String, BaseHandler> handlerBeans = context.getBeansOfType(BaseHandler.class);

        handlerBeans.forEach((name, bean) -> {
            Class<?> requestType = ResolvableType.forClass(bean.getClass()).as(BaseHandler.class).getGeneric(0).resolve();

            if (requestType != null) {
                handlerCache.put(requestType, bean);
            }
        });
    }

    /**
     * Executes a mediator request through its matching handler.
     *
     * @param request request instance
     * @param <T> request type
     * @param <R> response type
     * @return handler execution result
     */
    public <T extends Request, R> R execute(T request) {
        @SuppressWarnings("unchecked")
        BaseHandler<T, R> handler = (BaseHandler<T, R>) handlerCache.get(request.getClass());

        return handler.execute(request);
    }
}
