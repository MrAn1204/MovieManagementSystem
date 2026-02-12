package com.mms.mms_api.util.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.schedule.ScheduleCreateCommand;
import com.mms.mms_api.business.command.schedule.ScheduleUpdateCommand;
import com.mms.mms_api.business.service.validation.MovieValidationService;
import com.mms.mms_api.business.service.validation.RoomValidationService;
import com.mms.mms_api.business.service.validation.ScheduleValidationService;
import com.mms.mms_api.exception.ErrorDetail;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleValidator implements BaseValidator {
    private final ScheduleValidationService scheduleValidationService;

    private final MovieValidationService movieValidationService;

    private final RoomValidationService roomValidationService;

    public void validate(ScheduleCreateCommand command) {
        List<ErrorDetail> errors = new ArrayList<>();

        validateMovie(errors, command.getMovieId());
        validateRoom(errors, command.getRoomId());

        if (!errors.isEmpty()) {
            throw new InvalidInputException(errors);
        }
    }

    public void validate(ScheduleUpdateCommand command) {
        List<ErrorDetail> errors = new ArrayList<>();

        validateId(errors, command.getId());

        if (!errors.isEmpty()) {
            throw new ResourceNotFoundException(errors);
        }

        validateMovie(errors, command.getMovieId());
        validateRoom(errors, command.getRoomId());

        if (!errors.isEmpty()) {
            throw new InvalidInputException(errors);
        }
    }

    private void validateId(List<ErrorDetail> errors, @NonNull UUID id) {
        if (!scheduleValidationService.existsById(id)) {
            errors.add(new ErrorDetail("id", "schedule.notFound"));
        }
    }

    private void validateMovie(List<ErrorDetail> errors, @NonNull UUID id) {
        if (!movieValidationService.existsById(id)) {
            errors.add(new ErrorDetail("movie", "movie.notFound"));
        }
    }

    private void validateRoom(List<ErrorDetail> errors, @NonNull UUID id) {
        if (!roomValidationService.existsById(id)) {
            errors.add(new ErrorDetail("room", "room.notFound"));
        }
    }
}
