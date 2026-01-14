package com.mms.mms_api.business.query;

import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;

import lombok.Data;

@Data
public abstract class BaseGetByIdQuery implements BaseQuery {
    @NonNull
    private UUID id;

    protected BaseGetByIdQuery(UUID id) {
        this.id = Objects.requireNonNull(id);
    }
}
