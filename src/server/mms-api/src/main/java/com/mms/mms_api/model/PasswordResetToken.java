package com.mms.mms_api.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mms.mms_api.util.RandomTokenHelper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordResetToken extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private LocalDateTime expiry;

    @Column(nullable = false)
    private boolean used;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiry);
    }

    public PasswordResetToken(UUID userId) {
        this.token = RandomTokenHelper.generateToken();
        this.userId = userId;
        this.expiry = LocalDateTime.now().plusMinutes(15);
        this.used = false;
    }
}
