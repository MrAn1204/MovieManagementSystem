package com.mms.mms_api.util.validator;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.ticket.TicketCreateCommand;
import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.business.service.validation.PromotionValidationService;
import com.mms.mms_api.business.service.validation.ScheduleValidationService;
import com.mms.mms_api.business.service.validation.ScheduleSeatValidationService;
import com.mms.mms_api.business.service.validation.SeatValidationService;
import com.mms.mms_api.business.service.validation.UserValidationService;
import com.mms.mms_api.exception.ErrorSet;
import com.mms.mms_api.exception.ErrorType;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.Seat;

import lombok.AllArgsConstructor;

/**
 * Validator for ticket create and update command payloads.
 */
@Component
@AllArgsConstructor
public class TicketValidator implements BaseValidator {
    private final ScheduleValidationService scheduleValidationService;

    private final SeatValidationService seatValidationService;

    private final UserValidationService userValidationService;

    private final PromotionValidationService promotionValidationService;

    private final ScheduleSeatValidationService scheduleSeatValidationService;

    /**
     * Validates a ticket create command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>The referenced schedule exists.</li>
     *   <li>The referenced promotion exists, if provided.</li>
     *   <li>The referenced user exists.</li>
     *   <li>Each referenced seat and its schedule-seat mapping exist.</li>
     *   <li>No referenced seat belongs to a different room than the schedule's room.</li>
     *   <li>No referenced seat is already reserved.</li>
     * </ul>
     *
     * @param command the ticket create command to validate
     * @throws com.mms.mms_api.exception.ResourceNotFoundException if any seat or schedule-seat mapping is not found
     * @throws com.mms.mms_api.exception.InvalidInputException if any seat is reserved or belongs to a mismatched room
     */
    public void validate(TicketCreateCommand command) {
        ErrorSet errors = new ErrorSet();

        Schedule schedule = scheduleValidationService.getById(command.getScheduleId());
        List<Seat> seats = seatValidationService.getByIdIn(command.getSeatIds());
        List<ScheduleSeat> scheduleSeats = scheduleSeatValidationService.getByScheduleAndSeatIn(schedule, seats);

        validateSchedule(errors, schedule);
        validatePromotion(errors, command.getPromotionId());
        validateUser(errors, command.getUserId());

        for (ScheduleSeat scheduleSeat : scheduleSeats) {
            validateSeat(errors, scheduleSeat.getSeat());
            validateScheduleSeat(errors, scheduleSeat);
            errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);
        }

        for (ScheduleSeat scheduleSeat : scheduleSeats) {
            validateRoomMismatch(errors, scheduleSeat.getSchedule(), scheduleSeat.getSeat());
            validateReserved(errors, scheduleSeat);
            errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
        }

    }

    /**
     * Validates a ticket update command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>The referenced schedule, seat, and schedule-seat mapping exist.</li>
     *   <li>The referenced promotion exists, if provided.</li>
     *   <li>The seat does not belong to a different room than the schedule's room.</li>
     *   <li>The seat is not already reserved.</li>
     * </ul>
     *
     * @param command the ticket update command to validate
     * @throws com.mms.mms_api.exception.ResourceNotFoundException if the schedule, seat, or schedule-seat mapping is not found
     * @throws com.mms.mms_api.exception.InvalidInputException if the seat is reserved or belongs to a mismatched room
     */
    public void validate(TicketUpdateCommand command) {
        ErrorSet errors = new ErrorSet();

        Schedule schedule = scheduleValidationService.getById(command.getScheduleId());
        Seat seat = seatValidationService.getById(command.getSeatId());
        ScheduleSeat scheduleSeat = scheduleSeatValidationService.getById(schedule.getId(), seat.getId());

        validateSchedule(errors, schedule);
        validateSeat(errors, seat);
        validateScheduleSeat(errors, scheduleSeat);
        validatePromotion(errors, command.getPromotionId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateRoomMismatch(errors, schedule, seat);
        validateReserved(errors, scheduleSeat);

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    /**
     * Checks that a promotion with the given id exists and adds an error if not.
     * Skips validation if the id is null.
     *
     * @param errors the error accumulator
     * @param promotionId the promotion id to look up
     */
    private void validatePromotion(ErrorSet errors, UUID promotionId) {
        if (promotionId == null) {
            return;
        }

        if (!promotionValidationService.existsById(promotionId)) {
            errors.add("promotion", "promotion.notFound");
        }
    }

    /**
     * Checks that a user with the given id exists and adds an error if not.
     *
     * @param errors the error accumulator
     * @param userId the user id to look up
     */
    private void validateUser(ErrorSet errors, @NonNull UUID userId) {
        if (!userValidationService.existsById(userId)) {
            errors.add("user", "user.notFound");
        }
    }

    /**
     * Checks that the given schedule is not null and adds an error if it is.
     *
     * @param errors the error accumulator
     * @param schedule the schedule to check
     */
    private void validateSchedule(ErrorSet errors, Schedule schedule) {
        if (schedule == null) {
            errors.add("schedule", "schedule.notFound");
        }
    }

    /**
     * Checks that the given seat is not null and adds an error if it is.
     *
     * @param errors the error accumulator
     * @param seat the seat to check
     */
    private void validateSeat(ErrorSet errors, Seat seat) {
        if (seat == null) {
            errors.add("seat", "seat.notFound");
        }
    }

    /**
     * Checks that the given schedule-seat mapping is not null and adds an error if it is.
     *
     * @param errors the error accumulator
     * @param scheduleSeat the schedule-seat mapping to check
     */
    private void validateScheduleSeat(ErrorSet errors, ScheduleSeat scheduleSeat) {
        if (scheduleSeat == null) {
            errors.add("scheduleSeat", "schedule.seat.notFound");
        }
    }

    /**
     * Checks that the given schedule-seat is not already reserved and adds an error if it is.
     *
     * @param errors the error accumulator
     * @param scheduleSeat the schedule-seat mapping to check
     */
    private void validateReserved(ErrorSet errors, ScheduleSeat scheduleSeat) {
        if (scheduleSeat != null && scheduleSeat.isReserved()) {
            errors.add("seat", "ticket.seat.reserved");
        }
    }

    /**
     * Checks that the seat's room matches the schedule's room and adds an error if not.
     *
     * @param errors the error accumulator
     * @param schedule the schedule containing the expected room
     * @param seat the seat whose room is checked
     */
    private void validateRoomMismatch(ErrorSet errors, Schedule schedule, Seat seat) {
        if (!seat.getRoom().getId().equals(schedule.getRoom().getId())) {
            errors.add("room", "ticket.room.mismatch");
        }
    }
}
