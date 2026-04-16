package com.mms.mms_api.security;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mms.mms_api.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Security principal adapter that exposes {@link User} through Spring Security's
 * {@link UserDetails} contract.
 */
@Data
@AllArgsConstructor
public class UserInfo implements UserDetails {
    transient User user;

    /**
     * Returns authorities derived from assigned role names.
     *
     * @return granted authorities for the authenticated user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    /**
     * Returns the hashed password for authentication checks.
     *
     * @return encoded password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the login username.
     *
     * @return account username
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Returns the user identifier.
     *
     * @return unique user id
     */
    public UUID getId() {
        return user.getId();
    }

    /**
     * Returns the user's full name.
     *
     * @return display name
     */
    public String getFullname() {
        return user.getFullname();
    }

    /**
     * Returns the user's email address.
     *
     * @return email value
     */
    public String getEmail() {
        return user.getEmail();
    }
}
