package com.mms.mms_api.util.validator;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.seat.SeatCreateCommand;
import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.business.service.validation.RoomValidationService;
import com.mms.mms_api.business.service.validation.SeatValidationService;
import com.mms.mms_api.exception.ErrorLinkedList;
import com.mms.mms_api.exception.ErrorType;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.SeatType;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SeatValidator implements BaseValidator {
    private final SeatValidationService seatValidationService;

    private final RoomValidationService roomValidationService;

    public void validate(SeatCreateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateRoomId(errors, command.getRoomId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        Room room = roomValidationService.getById(command.getRoomId());

        validateRoomCapacity(errors, room);
        validatePosition(errors, room, command.getSeatColumn(), command.getSeatRow());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);

        if (command.getSeatType() == SeatType.COUPLE) {
            validateCoupleSeatPosition(errors, command.getSeatColumn(), command.getSeatRow(), room);
            errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
        }
    }

    public void validate(SeatUpdateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        Seat seat = seatValidationService.getById(command.getId());

        validatePosition(errors, seat.getRoom(), command.getSeatColumn(), command.getSeatRow(), seat.getId());
        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);

        if (command.getSeatType() == SeatType.COUPLE) {
            validateCoupleSeatPosition(errors, command.getSeatColumn(), command.getSeatRow(), seat.getRoom());
            errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
        }
    }

    private void validateId(ErrorLinkedList errors, @NonNull UUID id) {
        if (!seatValidationService.existsById(id)) {
            errors.add("id", "seat.notFound");
        }
    }

    private void validatePosition(ErrorLinkedList errors, Room room, int seatColumn, int seatRow) {
        if (seatColumn <= 0 || seatColumn > room.getRowLength()) {
            errors.add("seatColumn", "seat.column.invalid");
        }

        if (seatRow <= 0 || seatRow > room.getColumnLength()) {
            errors.add("seatRow", "seat.row.invalid");
        }
    }

    private void validatePosition(ErrorLinkedList errors, Room room, int seatColumn, int seatRow, UUID seatId) {
        validatePosition(errors, room, seatColumn, seatRow);

        if (room.getSeats().stream()
                .filter(existingSeat -> !existingSeat.getId().equals(seatId))
                .anyMatch(existingSeat -> existingSeat.getSeatRow() == seatRow && existingSeat.getSeatColumn() == seatColumn)) {
            errors.add("position", "seat.position.invalid");
        }
    }

    private void validateCoupleSeatPosition(ErrorLinkedList errors, int seatColumn, int seatRow, Room room) {
        int secondColumn = seatColumn + 1;

        if (secondColumn > room.getRowLength()) {
            errors.add("seatColumn", "seat.column.invalid");
        }

        Seat secondSeat = room.getSeats().stream()
                .filter(seat -> seat.getSeatRow() == seatRow && seat.getSeatColumn() == secondColumn)
                .findFirst().orElse(null);

        if (secondSeat != null && secondSeat.getSeatType() == SeatType.COUPLE) {
            errors.add("position", "seat.position.invalid");
        }
    }

    private void validateRoomId(ErrorLinkedList errors, @NonNull UUID roomId) {
        if (!roomValidationService.existsById(roomId)) {
            errors.add("room", "room.notFound");
        }
    }

    private void validateRoomCapacity(ErrorLinkedList errors, Room room) {
        int capacity = room.getRowLength() * room.getColumnLength();

        if (room.getSeats().size() >= capacity) {
            errors.add("room", "room.full");
        }
    }
}
