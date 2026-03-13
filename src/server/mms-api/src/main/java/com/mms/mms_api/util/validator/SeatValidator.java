package com.mms.mms_api.util.validator;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.seat.SeatCreateCommand;
import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.business.query.seat.SeatGetAllInRoomQuery;
import com.mms.mms_api.business.query.seat.SeatGetAllWithStatusQuery;
import com.mms.mms_api.business.service.validation.RoomValidationService;
import com.mms.mms_api.business.service.validation.ScheduleValidationService;
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

    private final ScheduleValidationService scheduleValidationService;

    public void validate(SeatCreateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateRoomId(errors, command.getRoomId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        Room room = roomValidationService.getById(command.getRoomId());

        validateRoomCapacity(errors, room);

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);

        validatePosition(errors, room, command.getSeatColumn(), command.getSeatRow());
        validateSeatType(errors, command.getSeatType());
        if (isCoupleSeat(command.getSeatType())) {
            validateCoupleSeatPosition(errors, command.getSeatColumn(), command.getSeatRow(), room);
        }

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    public void validate(SeatUpdateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateId(errors, command.getId());
        validateRoomId(errors, command.getRoomId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        Room room = roomValidationService.getById(command.getRoomId());

        validateSeatType(errors, command.getSeatType());
        validatePosition(errors, room, command.getSeatColumn(), command.getSeatRow());
        if (isCoupleSeat(command.getSeatType())) {
            validateCoupleSeatPosition(errors, command.getSeatColumn(), command.getSeatRow(), room);
        }

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    public void validate(SeatGetAllInRoomQuery query) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateRoomId(errors, query.getRoomId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);
    }

    public void validate(SeatGetAllWithStatusQuery query) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateSchedule(errors, query.getScheduleId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);
    }

    private boolean isCoupleSeat(String seatType) {
        try {
            return SeatType.valueOf(seatType) == SeatType.COUPLE;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private void validateId(ErrorLinkedList errors, @NonNull UUID id) {
        if (!seatValidationService.existsById(id)) {
            errors.add("id", "seat.notFound");
        }
    }

    private void validatePosition(ErrorLinkedList errors, Room room, int seatColumn, int seatRow) {
        if (seatColumn <= 0 || seatColumn > room.getColumnLength()) {
            errors.add("seatColumn", "seat.column.invalid");
        }

        if (seatRow <= 0 || seatRow > room.getRowLength()) {
            errors.add("seatRow", "seat.row.invalid");
        }

        if (room.getSeats().stream()
                .anyMatch((seat -> seat.getSeatRow() == seatRow && seat.getSeatColumn() == seatColumn))) {
            errors.add("position", "seat.position.invalid");
        }
    }

    private void validateSeatType(ErrorLinkedList errors, String seatType) {
        try {
            SeatType.valueOf(seatType);
        } catch (IllegalArgumentException e) {
            errors.add("seatType", "seat.type.invalid");
        }
    }

    private void validateCoupleSeatPosition(ErrorLinkedList errors, int seatColumn, int seatRow, Room room) {
        int secondColumn = seatColumn + 1;

        if (secondColumn > room.getColumnLength()) {
            errors.add("seatColumn", "seat.column.invalid");
        }

        Seat secondSeat = room.getSeats().stream()
                .filter(seat -> seat.getSeatRow() == seatRow && seat.getSeatColumn() == secondColumn)
                .findFirst().orElse(null);

        if (secondSeat == null || secondSeat.getSeatType() == SeatType.COUPLE) {
            errors.add("position", "seat.position.invalid");
        }
    }

    private void validateRoomId(ErrorLinkedList errors, @NonNull UUID roomId) {
        if (!roomValidationService.existsById(roomId)) {
            errors.add("room", "room.notFound");
        }
    }

    private void validateSchedule(ErrorLinkedList errors, @NonNull UUID scheduleId) {
        if (!scheduleValidationService.existsById(scheduleId)) {
            errors.add("schedule", "schedule.notFound");
        }
    }

    private void validateRoomCapacity(ErrorLinkedList errors, Room room) {
        int capacity = room.getRowLength() * room.getColumnLength();

        if (room.getSeats().size() >= capacity) {
            errors.add("room", "room.full");
        }
    }
}
