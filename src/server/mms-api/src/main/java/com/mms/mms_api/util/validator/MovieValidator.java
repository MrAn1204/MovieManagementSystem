package com.mms.mms_api.util.validator;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
import com.mms.mms_api.business.service.TalentValidationService;
import com.mms.mms_api.business.service.validation.GenreValidationService;
import com.mms.mms_api.business.service.validation.LanguageValidationService;
import com.mms.mms_api.business.service.validation.MovieValidationService;
import com.mms.mms_api.business.service.validation.StudioValidationService;
import com.mms.mms_api.exception.ErrorSet;
import com.mms.mms_api.exception.ErrorType;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Validates movie create and update commands.
 */
@Component
@AllArgsConstructor
public class MovieValidator implements BaseValidator {
    private final MovieValidationService movieValidationService;

    private final GenreValidationService genreValidationService;

    private final LanguageValidationService languageValidationService;

    private final StudioValidationService studioValidationService;

    private final TalentValidationService talentValidationService;

    /**
     * Validates a movie create command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>All referenced genres exist, if provided.</li>
     *   <li>The referenced language exists, if provided.</li>
     *   <li>All referenced studios exist, if provided.</li>
     *   <li>All referenced talents exist, if provided.</li>
     * </ul>
     *
     * @param command the movie create command to validate
     * @throws com.mms.mms_api.exception.InvalidInputException if any reference is invalid
     */
    public void validate(MovieCreateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateGenres(errors, command.getGenreIds());
        validateLanguage(errors, command.getLanguageId());
        validateStudios(errors, command.getStudioIds());
        validateTalents(errors, command.getTalentIds());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    /**
     * Validates a movie update command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>A movie with the given id exists.</li>
     *   <li>All referenced genres exist, if provided.</li>
     *   <li>The referenced language exists, if provided.</li>
     *   <li>All referenced studios exist, if provided.</li>
     *   <li>All referenced talents exist, if provided.</li>
     * </ul>
     *
     * @param command the movie update command to validate
     * @throws com.mms.mms_api.exception.ResourceNotFoundException if the movie is not found
     * @throws com.mms.mms_api.exception.InvalidInputException if any reference is invalid
     */
    public void validate(MovieUpdateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateGenres(errors, command.getGenreIds());
        validateLanguage(errors, command.getLanguageId());
        validateStudios(errors, command.getStudioIds());
        validateTalents(errors, command.getTalentIds());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    /**
     * Checks that a movie with the given id exists and adds an error if not.
     *
     * @param errors the error accumulator
     * @param id the movie id to look up
     */
    private void validateId(ErrorSet errors, @NonNull UUID id) {
        if (!movieValidationService.existsById(id)) {
            errors.add("id", "movie.notFound");
        }
    }

    /**
     * Checks that all genres in the given id list exist and adds an error if not.
     * Skips validation if the list is null or empty.
     *
     * @param errors the error accumulator
     * @param genreIds the list of genre ids to validate
     */
    private void validateGenres(ErrorSet errors, List<UUID> genreIds) {
        if (CollectionUtils.isEmpty(genreIds)) {
            return;
        }

        if (!genreValidationService.existsAllById(genreIds)) {
            errors.add("genres", "movie.genres.invalid");
        }
    }

    /**
     * Checks that the language with the given id exists and adds an error if not.
     * Skips validation if the id is null.
     *
     * @param errors the error accumulator
     * @param languageId the language id to validate
     */
    private void validateLanguage(ErrorSet errors, UUID languageId) {
        if (languageId == null) {
            return;
        }

        if (!languageValidationService.existsById(languageId)) {
            errors.add("language", "movie.language.invalid");
        }
    }

    /**
     * Checks that all studios in the given id list exist and adds an error if not.
     * Skips validation if the list is null or empty.
     *
     * @param errors the error accumulator
     * @param studioIds the list of studio ids to validate
     */
    private void validateStudios(ErrorSet errors, List<UUID> studioIds) {
        if (CollectionUtils.isEmpty(studioIds)) {
            return;
        }

        if (!studioValidationService.existsAllById(studioIds)) {
            errors.add("studios", "movie.studios.invalid");
        }
    }

    /**
     * Checks that all talents in the given id list exist and adds an error if not.
     * Skips validation if the list is null or empty.
     *
     * @param errors the error accumulator
     * @param talentIds the list of talent ids to validate
     */
    private void validateTalents(ErrorSet errors, List<UUID> talentIds) {
        if (CollectionUtils.isEmpty(talentIds)) {
            return;
        }

        if (!talentValidationService.existsAllById(talentIds)) {
            errors.add("talents", "movie.talents.invalid");
        }
    }
}
