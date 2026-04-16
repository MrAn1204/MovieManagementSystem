package com.mms.mms_api.util.validator;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.room.RoomCreateCommand;
import com.mms.mms_api.business.command.room.RoomUpdateCommand;
import com.mms.mms_api.business.service.validation.RoomValidationService;
import com.mms.mms_api.exception.ErrorSet;
import com.mms.mms_api.exception.ErrorType;
import com.mms.mms_api.model.Room;

import lombok.AllArgsConstructor;

/**
 * Validates room create and update commands.
 */
@Component
@AllArgsConstructor
public class RoomValidator implements BaseValidator {
    private final RoomValidationService roomValidationService;

    /**
     * Validates a room create command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>No other room with the same name exists.</li>
     * </ul>
     *
     * @param command the room create command to validate
     * @throws com.mms.mms_api.exception.InvalidInputException if the room name is already taken
     */
    public void validate(RoomCreateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateName(errors, command.getName());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    /**
     * Validates a room update command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>A room with the given id exists.</li>
     *   <li>No other room with the same name exists.</li>
     *   <li>The new row and column lengths do not reduce capacity below existing seats.</li>
     * </ul>
     *
     * @param command the room update command to validate
     * @throws com.mms.mms_api.exception.ResourceNotFoundException if the room is not found
     * @throws com.mms.mms_api.exception.InvalidInputException if the name is taken or the new capacity is too small
     */
    public void validate(RoomUpdateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateName(errors, command.getName(), command.getId());
        validateCapacity(errors, command.getId(), command.getRowLength(), command.getColumnLength());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    /**
     * Checks that a room with the given id exists and adds an error if not.
     *
     * @param errors the error accumulator
     * @param id the room id to look up
     */
    private void validateId(ErrorSet errors, @NonNull UUID id) {
        if (!roomValidationService.existsById(id)) {
            errors.add("id", "room.notFound");
        }
    }

    /**
     * Checks that no room with the given name already exists and adds an error if one does.
     *
     * @param errors the error accumulator
     * @param name the room name to check for uniqueness
     */
    private void validateName(ErrorSet errors, String name) {
        if (roomValidationService.existsByName(name)) {
            errors.add("name", "room.name.unique");
        }
    }

    /**
     * Checks that no other room with the given name already exists (excluding the current room) and adds an error if one does.
     *
     * @param errors the error accumulator
     * @param name the room name to check for uniqueness
     * @param id the id of the room being updated, excluded from the uniqueness check
     */
    private void validateName(ErrorSet errors, String name, UUID id) {
        if (roomValidationService.existsByNameAndIdNot(name, id)) {
            errors.add("name", "room.name.unique");
        }
    }

    /**
     * Checks that the new dimensions do not shrink below the room's existing seat layout.
     *
     * @param errors the error accumulator
     * @param id the room id to retrieve the room for seat layout comparison
     * @param rowLength the new number of rows
     * @param columnLength the new number of columns
     */
    private void validateCapacity(ErrorSet errors, @NonNull UUID id, int rowLength, int columnLength) {
        Room room = roomValidationService.getById(id);

        if (room.hasSeatFrom(columnLength, rowLength)) {
            errors.add("room", "room.capacity.invalid");
            return;
        }

        if (!room.hasSpace()) {
            errors.add("room", "room.full");
        }
    }
}
