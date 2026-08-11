package com.mms.mms_api.business.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RateLimitService {
    private static final int MAX_REQUESTS = 50;

    private static final Duration REFILL_DURATION = Duration.ofMinutes(1);

    /**
     * Key format: rate-limit:{version}:{method}:{path}:{clientId}
     * <br>
     * <strong>NOTE</strong>: Must change version number when changing bucket configuration
     */
    private static final String KEY_TEMPLATE = "rate-limit:v1:%s:%s:%s";

    private static final Bandwidth BUCKET_BANDWIDTH = Bandwidth.builder()
            .capacity(MAX_REQUESTS)
            .refillIntervally(MAX_REQUESTS, REFILL_DURATION)
            .build();

    private final ProxyManager<String> proxyManager;

    public boolean isAllowed(String clientId, String method, String path) {
        String key = String.format(KEY_TEMPLATE, method, path, clientId);

        Bucket bucket = proxyManager.builder()
                .build(key, () -> BucketConfiguration.builder().addLimit(BUCKET_BANDWIDTH).build());

        return bucket.tryConsume(1);
    }
}
