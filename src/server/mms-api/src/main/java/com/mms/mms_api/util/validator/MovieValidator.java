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

@Component
@AllArgsConstructor
public class MovieValidator implements BaseValidator {
    private final MovieValidationService movieValidationService;

    private final GenreValidationService genreValidationService;

    private final LanguageValidationService languageValidationService;

    private final StudioValidationService studioValidationService;

    private final TalentValidationService talentValidationService;

    public void validate(MovieCreateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateGenres(errors, command.getGenreIds());
        validateLanguage(errors, command.getLanguageId());
        validateStudios(errors, command.getStudioIds());
        validateTalents(errors, command.getTalentIds());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

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

    private void validateId(ErrorSet errors, @NonNull UUID id) {
        if (!movieValidationService.existsById(id)) {
            errors.add("id", "movie.notFound");
        }
    }

    private void validateGenres(ErrorSet errors, List<UUID> genreIds) {
        if (CollectionUtils.isEmpty(genreIds)) {
            return;
        }

        if (!genreValidationService.existsAllById(genreIds)) {
            errors.add("genres", "movie.genres.invalid");
        }
    }

    private void validateLanguage(ErrorSet errors, UUID languageId) {
        if (languageId == null) {
            return;
        }

        if (!languageValidationService.existsById(languageId)) {
            errors.add("language", "movie.language.invalid");
        }
    }

    private void validateStudios(ErrorSet errors, List<UUID> studioIds) {
        if (CollectionUtils.isEmpty(studioIds)) {
            return;
        }

        if (!studioValidationService.existsAllById(studioIds)) {
            errors.add("studios", "movie.studios.invalid");
        }
    }

    private void validateTalents(ErrorSet errors, List<UUID> talentIds) {
        if (CollectionUtils.isEmpty(talentIds)) {
            return;
        }

        if (!talentValidationService.existsAllById(talentIds)) {
            errors.add("talents", "movie.talents.invalid");
        }
    }
}
