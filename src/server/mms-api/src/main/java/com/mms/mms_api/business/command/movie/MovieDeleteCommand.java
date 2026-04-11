package com.mms.mms_api.business.command.movie;

import java.util.Objects;
import java.util.UUID;

import com.mms.mms_api.business.command.BaseDeleteCommand;

/**
 * Command payload for deleting movies.
 */
public class MovieDeleteCommand extends BaseDeleteCommand {
    public MovieDeleteCommand(UUID id) {
        super(Objects.requireNonNull(id));
    }
}
