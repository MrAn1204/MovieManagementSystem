package com.mms.mms_api.mediator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.handler.BaseHandler;

@Component
public class RequestMediator {
    private final Map<Class<?>, BaseHandler<?, ?>> handlerCache = new HashMap<>();

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

    public <T extends Request, R> R execute(T request) {
        @SuppressWarnings("unchecked")
        BaseHandler<T, R> handler = (BaseHandler<T, R>) handlerCache.get(request.getClass());

        return handler.execute(request);
    }
}
