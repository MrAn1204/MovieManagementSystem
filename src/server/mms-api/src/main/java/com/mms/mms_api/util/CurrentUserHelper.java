package com.mms.mms_api.util;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserHelper {
    private Authentication getAuthentication() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

    public boolean isAuthenticated() {
        Authentication auth = getAuthentication();

        return auth != null && auth.isAuthenticated();
    }

    private boolean hasRole(String role) {
        return getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }

    @SuppressWarnings("null")
    private boolean hasRoles(Collection<String> roles) {
        List<String> authorities = getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return authorities.containsAll(roles);
    }

    public boolean isAdmin() {
        return hasRole(RoleName.ADMIN);
    }

    public boolean isAuditable() {
        List<String> auditableRoles = List.of(RoleName.ADMIN);

        return hasRoles(auditableRoles);
    }
}

final class RoleName {
    private RoleName() {
    }

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
}