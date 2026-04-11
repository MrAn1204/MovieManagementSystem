package com.mms.mms_api.business.command;

import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;

import lombok.Data;

/**
 * Base type for update commands that require a non-null entity identifier.
 */
@Data
public abstract class BaseUpdateCommand implements BaseCommand {
    private UUID id;

    /**
     * Sets the target entity id.
     *
     * @param id entity identifier
     */
    public void setId(UUID id) {
        this.id = Objects.requireNonNull(id, "Cannot set id to null.");
    }

    /**
     * Gets the assigned target entity id.
     *
     * @return non-null entity identifier
     */
    public @NonNull UUID getId() {
        return Objects.requireNonNull(id, "No id was assigned.");
    }
}
