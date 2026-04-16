package com.mms.mms_api.business.command;

import java.util.Objects;
import java.util.UUID;

import org.springframework.lang.NonNull;

import lombok.Data;

/**
 * Base type for delete commands that target an existing entity id.
 */
@Data
public abstract class BaseDeleteCommand implements BaseCommand {
    @NonNull
    private UUID id;

    /**
     * Creates a delete command.
     *
     * @param id identifier of the entity to delete
     */
    protected BaseDeleteCommand(UUID id) {
        this.id = Objects.requireNonNull(id);
    }
}
