package com.mms.mms_api.util.validator;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
import com.mms.mms_api.business.service.TalentValidationService;
import com.mms.mms_api.business.service.validation.GenreValidationService;
import com.mms.mms_api.business.service.validation.LanguageValidationService;
import com.mms.mms_api.business.service.validation.MovieValidationService;
import com.mms.mms_api.business.service.validation.StudioValidationService;
import com.mms.mms_api.exception.ErrorLinkedList;
import com.mms.mms_api.exception.ErrorType;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@AllArgsConstructor
public class MovieValidator implements BaseValidator {
    private final MovieValidationService movieValidationService;

    private final GenreValidationService genreValidationService;

    private final LanguageValidationService languageValidationService;

    private final StudioValidationService studioValidationService;

    private final TalentValidationService talentValidationService;

    public void validate(MovieCreateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateGenres(errors, command.getGenreIds());
        validateLanguage(errors, command.getLanguageId());
        validateStudios(errors, command.getStudioIds());
        validateTalents(errors, command.getTalentIds());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    public void validate(MovieUpdateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateGenres(errors, command.getGenreIds());
        validateLanguage(errors, command.getLanguageId());
        validateStudios(errors, command.getStudioIds());
        validateTalents(errors, command.getTalentIds());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    private void validateId(ErrorLinkedList errors, @NonNull UUID id) {
        if (!movieValidationService.existsById(id)) {
            errors.add("id", "movie.notFound");
        }
    }

    private void validateGenres(ErrorLinkedList errors, List<UUID> genreIds) {
        if (CollectionUtils.isEmpty(genreIds)) {
            return;
        }

        if (!genreValidationService.existsAllById(genreIds)) {
            errors.add("genres", "movie.genres.invalid");
        }
    }

    private void validateLanguage(ErrorLinkedList errors, UUID languageId) {
        if (languageId == null) {
            return;
        }

        if (!languageValidationService.existsById(languageId)) {
            errors.add("language", "movie.language.invalid");
        }
    }

    private void validateStudios(ErrorLinkedList errors, List<UUID> studioIds) {
        if (CollectionUtils.isEmpty(studioIds)) {
            return;
        }

        if (!studioValidationService.existsAllById(studioIds)) {
            errors.add("studios", "movie.studios.invalid");
        }
    }

    private void validateTalents(ErrorLinkedList errors, List<UUID> talentIds) {
        if (CollectionUtils.isEmpty(talentIds)) {
            return;
        }

        if (!talentValidationService.existsAllById(talentIds)) {
            errors.add("talents", "movie.talents.invalid");
        }
    }
}
