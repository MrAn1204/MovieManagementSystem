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

    private void validatePromotion(ErrorSet errors, UUID promotionId) {
        if (promotionId == null) {
            return;
        }

        if (!promotionValidationService.existsById(promotionId)) {
            errors.add("promotion", "promotion.notFound");
        }
    }

    private void validateUser(ErrorSet errors, @NonNull UUID userId) {
        if (!userValidationService.existsById(userId)) {
            errors.add("user", "user.notFound");
        }
    }

    private void validateSchedule(ErrorSet errors, Schedule schedule) {
        if (schedule == null) {
            errors.add("schedule", "schedule.notFound");
        }
    }

    private void validateSeat(ErrorSet errors, Seat seat) {
        if (seat == null) {
            errors.add("seat", "seat.notFound");
        }
    }

    private void validateScheduleSeat(ErrorSet errors, ScheduleSeat scheduleSeat) {
        if (scheduleSeat == null) {
            errors.add("scheduleSeat", "schedule.seat.notFound");
        }
    }

    private void validateReserved(ErrorSet errors, ScheduleSeat scheduleSeat) {
        if (scheduleSeat != null && scheduleSeat.isReserved()) {
            errors.add("seat", "ticket.seat.reserved");
        }
    }

    private void validateRoomMismatch(ErrorSet errors, Schedule schedule, Seat seat) {
        if (!seat.getRoom().getId().equals(schedule.getRoom().getId())) {
            errors.add("room", "ticket.room.mismatch");
        }
    }
}
