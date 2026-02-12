package com.mms.mms_api.util.validator;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.schedule.ScheduleCreateCommand;
import com.mms.mms_api.business.command.schedule.ScheduleUpdateCommand;
import com.mms.mms_api.business.service.validation.MovieValidationService;
import com.mms.mms_api.business.service.validation.RoomValidationService;
import com.mms.mms_api.business.service.validation.ScheduleValidationService;
import com.mms.mms_api.exception.ErrorLinkedList;
import com.mms.mms_api.exception.ErrorType;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleValidator implements BaseValidator {
    private final ScheduleValidationService scheduleValidationService;

    private final MovieValidationService movieValidationService;

    private final RoomValidationService roomValidationService;

    public void validate(ScheduleCreateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateMovie(errors, command.getMovieId());
        validateRoom(errors, command.getRoomId());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    public void validate(ScheduleUpdateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateMovie(errors, command.getMovieId());
        validateRoom(errors, command.getRoomId());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    private void validateId(ErrorLinkedList errors, @NonNull UUID id) {
        if (!scheduleValidationService.existsById(id)) {
            errors.add("id", "schedule.notFound");
        }
    }

    private void validateMovie(ErrorLinkedList errors, @NonNull UUID id) {
        if (!movieValidationService.existsById(id)) {
            errors.add("movie", "movie.notFound");
        }
    }

    private void validateRoom(ErrorLinkedList errors, @NonNull UUID id) {
        if (!roomValidationService.existsById(id)) {
            errors.add("room", "room.notFound");
        }
    }
}
