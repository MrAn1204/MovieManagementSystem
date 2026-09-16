package com.mms.mms_api.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.mms.mms_api.common.AppConstant;
import com.mms.mms_api.security.UserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Helper component for generating and validating JWT tokens.
 */
@Component
public class JwtHelper {
    /**
     * Generates a signed token containing user identity and role claims.
     */
    public String generateToken(UserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();

        UUID id = userInfo.getId();

        String fullname = userInfo.getFullname();

        String email = userInfo.getEmail();

        List<String> roles = userInfo.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        claims.put("id", id);
        claims.put("fullname", fullname);
        claims.put("email", email);
        claims.put("roles", roles);

        Date now = new Date();

        return Jwts.builder()
                .claims(claims)
                .subject(userInfo.getUsername())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + AppConstant.LOGIN_EXPIRY))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(System.getenv("JWT_SECRET_KEY").getBytes());
    }

    /**
     * Extracts the username subject from the token.
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extracts role claims from the token payload.
     */
    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("roles");
    }

    public long extractLifespan(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        long diff = expiration.getTime() - System.currentTimeMillis();

        return Math.max(0, diff);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    /**
     * Validates that a token belongs to the username and is not expired.
     */
    public boolean isTokenValid(String token) {
        return token != null && !token.isEmpty() && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
