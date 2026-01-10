package com.mms.mms_api.business.command;

import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public abstract class BaseUpdateCommand {
    private UUID id;

    public void setId(UUID id) {
        this.id = Objects.requireNonNull(id, "Cannot set id to null.");
    }

    public @NonNull UUID getId() {
        return Objects.requireNonNull(id, "No id was assigned.");
    }
}
