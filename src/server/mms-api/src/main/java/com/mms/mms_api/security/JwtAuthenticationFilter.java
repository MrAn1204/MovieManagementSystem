package com.mms.mms_api.security;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.mms.mms_api.business.service.UserInfoService;
import com.mms.mms_api.common.AppConstant;
import com.mms.mms_api.util.JwtHelper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Request filter that authenticates users from Bearer JWT tokens.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtHelper jwtHelper;

    private UserInfoService authService;

    private HandlerExceptionResolver resolver;

    /**
     * Creates a JWT authentication filter.
     *
     * @param jwtHelper JWT helper utility
     * @param authService user detail service
     * @param resolver exception resolver for filter-level failures
     */
    public JwtAuthenticationFilter(JwtHelper jwtHelper, UserInfoService authService,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtHelper = jwtHelper;
        this.authService = authService;
        this.resolver = resolver;
    }

    /**
     * Resolves and validates a Bearer token, then populates security context.
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @param filterChain remaining filter chain
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwt(request);

            if (jwt != null && jwtHelper.isTokenValid(jwt)) {
                String username = jwtHelper.extractUsername(jwt);
                UserDetails userDetails = authService.loadUserByUsername(username);

                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            resolver.resolveException(request, response, null, e);
        }
    }

    private String getJwt(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        } else if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(c -> AppConstant.LOGIN_COOKIE_NAME.equals(c.getName()))
                    .map(c -> c != null ? c.getValue() : null)
                    .findFirst()
                    .orElse(null);
        }

        return null;
    }
}
