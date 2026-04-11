package com.mms.mms_api.business.query;

import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;

import lombok.Data;

/**
 * Base type for get-by-id queries.
 */
@Data
public abstract class BaseGetByIdQuery implements BaseQuery {
    @NonNull
    private UUID id;

    /**
     * Creates a get-by-id query.
     *
     * @param id entity identifier
     */
    protected BaseGetByIdQuery(UUID id) {
        this.id = Objects.requireNonNull(id);
    }
}
