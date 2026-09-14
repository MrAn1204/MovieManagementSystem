package com.mms.mms_api.util.validator;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.seat.SeatCreateCommand;
import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.business.service.validation.RoomValidationService;
import com.mms.mms_api.business.service.validation.SeatValidationService;
import com.mms.mms_api.exception.ErrorSet;
import com.mms.mms_api.exception.ErrorType;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.SeatType;

import lombok.AllArgsConstructor;

/**
 * Validator for seat create and update command payloads.
 */
@Component
@AllArgsConstructor
public class SeatValidator implements BaseValidator {
    private final SeatValidationService seatValidationService;

    private final RoomValidationService roomValidationService;

    /**
     * Validates a seat create command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>The referenced room exists.</li>
     *   <li>The room has available capacity for a new seat.</li>
     *   <li>The seat position (column and row) is valid within the room.</li>
     *   <li>If the seat type is couple, the adjacent column position is valid and unoccupied by another couple seat.</li>
     * </ul>
     *
     * @param command the seat create command to validate
     * @throws com.mms.mms_api.exception.ResourceNotFoundException if the room is not found
     * @throws com.mms.mms_api.exception.InvalidInputException if the room is full, the position is invalid, or the couple seat position is taken
     */
    public void validate(SeatCreateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateRoomId(errors, command.getRoomId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        Room room = roomValidationService.getById(command.getRoomId());

        validateRoomCapacity(errors, room);
        validatePosition(errors, room, command.getSeatColumn(), command.getSeatRow(), null);

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);

        if (command.getSeatType() == SeatType.COUPLE) {
            validateCoupleSeatPosition(errors, command.getSeatColumn(), command.getSeatRow(), room);
            errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
        }
    }

    /**
     * Validates a seat update command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>A seat with the given id exists.</li>
     *   <li>The new seat position (column and row) is valid within the seat's room and not occupied by another seat.</li>
     *   <li>If the seat type is couple, the adjacent column position is valid and unoccupied by another couple seat.</li>
     * </ul>
     *
     * @param command the seat update command to validate
     * @throws com.mms.mms_api.exception.ResourceNotFoundException if the seat is not found
     * @throws com.mms.mms_api.exception.InvalidInputException if the position is invalid or the couple seat position is taken
     */
    public void validate(SeatUpdateCommand command) {
        ErrorSet errors = new ErrorSet();

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

    /**
     * Checks that a seat with the given id exists and adds an error if not.
     *
     * @param errors the error accumulator
     * @param id the seat id to look up
     */
    private void validateId(ErrorSet errors, @NonNull UUID id) {
        if (!seatValidationService.existsById(id)) {
            errors.add("id", "seat.notFound");
        }
    }

    /**
     * Checks that the given column and row are within the room's bounds and not already occupied by another seat.
     *
     * @param errors the error accumulator
     * @param room the room to validate the position against
     * @param seatColumn the column index of the seat
     * @param seatRow the row index of the seat
     * @param seatId the id of the seat being updated, excluded from the occupancy check
     */
    private void validatePosition(ErrorSet errors, Room room, int seatColumn, int seatRow, UUID seatId) {
        boolean validCol = room.isValidColumn(seatColumn);
        boolean validRow = room.isValidRow(seatRow);

        if (!validCol) {
            errors.add("seatColumn", "seat.column.invalid");
        }

        if (!validRow) {
            errors.add("seatRow", "seat.row.invalid");
        }

        if (validCol && validRow && room.hasSeatAt(seatRow, seatColumn, seatId)) {
            errors.add("position", "seat.position.invalid");
        }
    }

    /**
     * Checks that the next column is valid and not already occupied by a couple seat.
     *
     * @param errors the error accumulator
     * @param seatColumn the column index of the couple seat's first position
     * @param seatRow the row index of the seat
     * @param room the room to validate the adjacent position against
     */
    private void validateCoupleSeatPosition(ErrorSet errors, int seatColumn, int seatRow, Room room) {
        int secondColumn = seatColumn + 1;

        if (!room.isValidColumn(secondColumn)) {
            errors.add("seatColumn", "seat.column.invalid");
        }

        Seat secondSeat = room.getSeatAt(seatRow, secondColumn);

        if (secondSeat != null && secondSeat.getSeatType() == SeatType.COUPLE) {
            errors.add("position", "seat.position.invalid");
        }
    }

    /**
     * Checks that a room with the given id exists and adds an error if not.
     *
     * @param errors the error accumulator
     * @param roomId the room id to look up
     */
    private void validateRoomId(ErrorSet errors, @NonNull UUID roomId) {
        if (!roomValidationService.existsById(roomId)) {
            errors.add("room", "room.notFound");
        }
    }

    /**
     * Checks that the room has available capacity for at least one more seat.
     *
     * @param errors the error accumulator
     * @param room the room to check
     */
    private void validateRoomCapacity(ErrorSet errors, Room room) {
        if (!room.hasSpace()) {
            errors.add("room", "room.full");
        }
    }
}
