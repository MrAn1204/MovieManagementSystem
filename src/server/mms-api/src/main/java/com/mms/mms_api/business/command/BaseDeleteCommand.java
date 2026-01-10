package com.mms.mms_api.business.command;

import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public abstract class BaseDeleteCommand {
    @NonNull
    private UUID id;

    protected BaseDeleteCommand(UUID id) {
        this.id = Objects.requireNonNull(id);
    }
}
