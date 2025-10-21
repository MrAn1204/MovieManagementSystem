package com.mms.mms_api.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.mms.mms_api.security.UserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {
    private static final long EXPIRATION_TIME = 86400000;

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
                .expiration(new Date(now.getTime() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(System.getenv("JWT_SECRET_KEY").getBytes());
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("roles");
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
