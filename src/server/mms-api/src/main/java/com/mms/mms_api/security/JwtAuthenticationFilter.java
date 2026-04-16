package com.mms.mms_api.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.mms.mms_api.business.service.UserInfoService;
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
            String authHeader = request.getHeader("Authorization");

            String jwt = null;
            String username = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                username = jwtHelper.extractUsername(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = authService.loadUserByUsername(username);

                if (jwtHelper.isTokenValid(jwt, userDetails.getUsername())) {
                    Collection<? extends GrantedAuthority> authority = userDetails.getAuthorities();

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, authority);

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            resolver.resolveException(request, response, null, e);
        }
    }
}
