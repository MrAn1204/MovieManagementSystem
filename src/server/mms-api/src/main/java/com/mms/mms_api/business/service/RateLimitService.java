package com.mms.mms_api.business.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RateLimitService {
    private static final int MAX_REQUESTS = 10;

    private static final Duration WINDOW_DURATION = Duration.ofMinutes(1);

    private final StringRedisTemplate redis;

    public boolean isAllowed(String clientId, String method, String path) {
        String key = method + ":" + path + ":" + clientId;

        Long count = redis.opsForValue().increment(key, 1);

        if (count == null) {
            return false;
        }

        if (count == 1) {
            redis.expire(key, WINDOW_DURATION);
        }

        return count <= MAX_REQUESTS;
    }
}
