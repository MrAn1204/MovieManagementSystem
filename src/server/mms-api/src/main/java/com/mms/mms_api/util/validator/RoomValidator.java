package com.mms.mms_api.util.validator;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.room.RoomCreateCommand;
import com.mms.mms_api.business.command.room.RoomUpdateCommand;
import com.mms.mms_api.business.service.validation.RoomValidationService;
import com.mms.mms_api.exception.ErrorLinkedList;
import com.mms.mms_api.exception.ErrorType;
import com.mms.mms_api.model.Room;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RoomValidator implements BaseValidator {
    private final RoomValidationService roomValidationService;

    public void validate(RoomCreateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateName(errors, command.getName());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    public void validate(RoomUpdateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateName(errors, command.getName(), command.getId());
        validateCapacity(errors, command.getId(), command.getRowLength(), command.getColumnLength());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    private void validateId(ErrorLinkedList errors, @NonNull UUID id) {
        if (!roomValidationService.existsById(id)) {
            errors.add("id", "room.notFound");
        }
    }

    private void validateName(ErrorLinkedList errors, String name) {
        if (roomValidationService.existsByName(name)) {
            errors.add("name", "room.name.unique");
        }
    }

    private void validateName(ErrorLinkedList errors, String name, UUID id) {
        if (roomValidationService.existsByNameAndIdNot(name, id)) {
            errors.add("name", "room.name.unique");
        }
    }

    private void validateCapacity(ErrorLinkedList errors, @NonNull UUID id, int rowLength, int columnLength) {
        Room room = roomValidationService.getById(id);

        if (room.getSeats().stream()
                .anyMatch(seat -> seat.getSeatRow() > rowLength || seat.getSeatColumn() > columnLength)) {
            errors.add("room", "room.capacity.invalid");
            return;
        }

        if (room.getSeats().size() > rowLength * columnLength) {
            errors.add("room", "room.full");
        }
    }
}
