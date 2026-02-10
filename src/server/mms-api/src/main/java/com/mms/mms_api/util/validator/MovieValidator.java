package com.mms.mms_api.util.validator;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
import com.mms.mms_api.business.service.TalentValidationService;
import com.mms.mms_api.business.service.validation.GenreValidationService;
import com.mms.mms_api.business.service.validation.LanguageValidationService;
import com.mms.mms_api.business.service.validation.MovieValidationService;
import com.mms.mms_api.business.service.validation.StudioValidationService;
import com.mms.mms_api.exception.ErrorDetail;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
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
        List<ErrorDetail> errors = new ArrayList<>();

        validateGenres(errors, command.getGenreIds());
        validateLanguage(errors, command.getLanguageId());
        validateStudios(errors, command.getStudioIds());
        validateTalents(errors, command.getTalentIds());

        if (!errors.isEmpty()) {
            throw new InvalidInputException(errors);
        }
    }

    public void validate(MovieUpdateCommand command) {
        List<ErrorDetail> errors = new ArrayList<>();

        validateId(errors, command.getId());

        if (!errors.isEmpty()) {
            throw new ResourceNotFoundException(errors);
        }

        validateGenres(errors, command.getGenreIds());
        validateLanguage(errors, command.getLanguageId());
        validateStudios(errors, command.getStudioIds());
        validateTalents(errors, command.getTalentIds());

        if (!errors.isEmpty()) {
            throw new InvalidInputException(errors);
        }
    }

    private void validateId(List<ErrorDetail> errors, @NonNull UUID id) {
        if (!movieValidationService.existsById(id)) {
            errors.add(new ErrorDetail("id", "movie.notFound"));
        }
    }

    private void validateGenres(List<ErrorDetail> errors, List<UUID> genreIds) {
        if (CollectionUtils.isEmpty(genreIds)) {
            return;
        }

        if (!genreValidationService.existsAllById(genreIds)) {
            errors.add(new ErrorDetail("genres", "movie.genres.invalid"));
        }
    }

    private void validateLanguage(List<ErrorDetail> errors, UUID languageId) {
        if (languageId == null) {
            return;
        }

        if (!languageValidationService.existsById(languageId)) {
            errors.add(new ErrorDetail("language", "movie.language.invalid"));
        }
    }

    private void validateStudios(List<ErrorDetail> errors, List<UUID> studioIds) {
        if (CollectionUtils.isEmpty(studioIds)) {
            return;
        }

        if (!studioValidationService.existsAllById(studioIds)) {
            errors.add(new ErrorDetail("studios", "movie.studios.invalid"));
        }
    }

    private void validateTalents(List<ErrorDetail> errors, List<UUID> talentIds) {
        if (CollectionUtils.isEmpty(talentIds)) {
            return;
        }

        if (!talentValidationService.existsAllById(talentIds)) {
            errors.add(new ErrorDetail("talents", "movie.talents.invalid"));
        }
    }
}
