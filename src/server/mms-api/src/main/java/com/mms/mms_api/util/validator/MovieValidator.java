package com.mms.mms_api.util.validator;

import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Genre;
import com.mms.mms_api.model.Language;
import com.mms.mms_api.model.Studio;
import com.mms.mms_api.model.Talent;

import java.util.List;
import java.util.UUID;

public class MovieValidator {
    private MovieValidator() {}

    public static void validateGenres(List<UUID> genreIds, List<Genre> mappedGenres) {
        if (genreIds.size() != mappedGenres.size()) {
            throw new InvalidInputException("movie.genres.invalid");
        }
    }

    public static void validateLanguage(Language mappedLanguage) {
        if (mappedLanguage == null) {
            throw new InvalidInputException("movie.language.invalid");
        }
    }

    public static void validateStudios(List<UUID> studioIds, List<Studio> mappedStudios) {
        if (studioIds.size() != mappedStudios.size()) {
            throw new InvalidInputException("movie.studios.invalid");
        }
    }

    public static void validateTalents(List<UUID> talentIds, List<Talent> mappedTalents) {
        if (talentIds.size() != mappedTalents.size()) {
            throw new InvalidInputException("movie.talents.invalid");
        }
    }
}
