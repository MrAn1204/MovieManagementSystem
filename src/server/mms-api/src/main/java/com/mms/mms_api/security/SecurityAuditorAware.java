package com.mms.mms_api.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Implementation of AuditorAware for Spring Data auditing.
 */
public class SecurityAuditorAware implements AuditorAware<String> {
    /**
     * Retrieves the currently authenticated user from the Security Context.
     *
     * @return an Optional containing the username of the authenticated user,
     *         or an empty Optional if no user is authenticated
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName);
    }
}
