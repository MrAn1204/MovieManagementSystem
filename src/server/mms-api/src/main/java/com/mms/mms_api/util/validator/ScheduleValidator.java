package com.mms.mms_api.util.validator;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.schedule.ScheduleCreateCommand;
import com.mms.mms_api.business.command.schedule.ScheduleUpdateCommand;
import com.mms.mms_api.business.service.validation.MovieValidationService;
import com.mms.mms_api.business.service.validation.RoomValidationService;
import com.mms.mms_api.business.service.validation.ScheduleValidationService;
import com.mms.mms_api.exception.ErrorSet;
import com.mms.mms_api.exception.ErrorType;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleValidator implements BaseValidator {
    private final ScheduleValidationService scheduleValidationService;

    private final MovieValidationService movieValidationService;

    private final RoomValidationService roomValidationService;

    public void validate(ScheduleCreateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateMovie(errors, command.getMovieId());
        validateRoom(errors, command.getRoomId());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    public void validate(ScheduleUpdateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateMovie(errors, command.getMovieId());
        validateRoom(errors, command.getRoomId());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    private void validateId(ErrorSet errors, @NonNull UUID id) {
        if (!scheduleValidationService.existsById(id)) {
            errors.add("id", "schedule.notFound");
        }
    }

    private void validateMovie(ErrorSet errors, @NonNull UUID id) {
        if (!movieValidationService.existsById(id)) {
            errors.add("movie", "movie.notFound");
        }
    }

    private void validateRoom(ErrorSet errors, @NonNull UUID id) {
        if (!roomValidationService.existsById(id)) {
            errors.add("room", "room.notFound");
        }
    }
}
