package com.mms.mms_api.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Access denied handler that delegates authorization failures to the global
 * exception resolver.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final HandlerExceptionResolver resolver;

    /**
     * Creates an access denied handler.
     *
     * @param resolver exception resolver bean
     */
    public CustomAccessDeniedHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    /**
     * Handles authorization failures for authenticated users.
     *
     * @param request HTTP request
     * @param response HTTP response
     * @param accessDeniedException access denied exception
     * @throws IOException when response writing fails
     * @throws ServletException when servlet processing fails
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        resolver.resolveException(request, response, null, accessDeniedException);
    }
}
