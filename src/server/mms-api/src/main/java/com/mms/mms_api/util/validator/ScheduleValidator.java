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

/**
 * Validates schedule create and update commands.
 */
@Component
@AllArgsConstructor
public class ScheduleValidator implements BaseValidator {
    private final ScheduleValidationService scheduleValidationService;

    private final MovieValidationService movieValidationService;

    private final RoomValidationService roomValidationService;

    /**
     * Validates a schedule create command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>The referenced movie exists.</li>
     *   <li>The referenced room exists.</li>
     * </ul>
     *
     * @param command the schedule create command to validate
     * @throws com.mms.mms_api.exception.InvalidInputException if the movie or room reference is invalid
     */
    public void validate(ScheduleCreateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateMovie(errors, command.getMovieId());
        validateRoom(errors, command.getRoomId());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    /**
     * Validates a schedule update command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>A schedule with the given id exists.</li>
     *   <li>The referenced movie exists.</li>
     *   <li>The referenced room exists.</li>
     * </ul>
     *
     * @param command the schedule update command to validate
     * @throws com.mms.mms_api.exception.ResourceNotFoundException if the schedule is not found
     * @throws com.mms.mms_api.exception.InvalidInputException if the movie or room reference is invalid
     */
    public void validate(ScheduleUpdateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateMovie(errors, command.getMovieId());
        validateRoom(errors, command.getRoomId());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    /**
     * Checks that a schedule with the given id exists and adds an error if not.
     *
     * @param errors the error accumulator
     * @param id the schedule id to look up
     */
    private void validateId(ErrorSet errors, @NonNull UUID id) {
        if (!scheduleValidationService.existsById(id)) {
            errors.add("id", "schedule.notFound");
        }
    }

    /**
     * Checks that a movie with the given id exists and adds an error if not.
     *
     * @param errors the error accumulator
     * @param id the movie id to look up
     */
    private void validateMovie(ErrorSet errors, @NonNull UUID id) {
        if (!movieValidationService.existsById(id)) {
            errors.add("movie", "movie.notFound");
        }
    }

    /**
     * Checks that a room with the given id exists and adds an error if not.
     *
     * @param errors the error accumulator
     * @param id the room id to look up
     */
    private void validateRoom(ErrorSet errors, @NonNull UUID id) {
        if (!roomValidationService.existsById(id)) {
            errors.add("room", "room.notFound");
        }
    }
}
