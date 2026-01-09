package com.mms.mms_api.util.validator;

import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Genre;
import com.mms.mms_api.model.Language;
import com.mms.mms_api.model.Studio;
import com.mms.mms_api.model.Talent;

import java.util.List;
import java.util.UUID;

import org.springframework.util.CollectionUtils;

public class MovieValidator {
    private MovieValidator() {
    }

    public static void validateGenres(List<UUID> genreIds, List<Genre> mappedGenres) {
        if (CollectionUtils.isEmpty(genreIds)) {
            return;
        }

        if (mappedGenres != null && genreIds.size() != mappedGenres.size()) {
            throw new InvalidInputException("movie.genres.invalid");
        }
    }

    public static void validateLanguage(UUID languageId, Language mappedLanguage) {
        if (languageId == null) {
            return;
        }
        
        if (mappedLanguage == null) {
            throw new InvalidInputException("movie.language.invalid");
        }
    }

    public static void validateStudios(List<UUID> studioIds, List<Studio> mappedStudios) {
        if (CollectionUtils.isEmpty(studioIds)) {
            return;
        }

        if (mappedStudios != null && studioIds.size() != mappedStudios.size()) {
            throw new InvalidInputException("movie.studios.invalid");
        }
    }

    public static void validateTalents(List<UUID> talentIds, List<Talent> mappedTalents) {
        if (CollectionUtils.isEmpty(talentIds)) {
            return;
        }

        if (mappedTalents != null && talentIds.size() != mappedTalents.size()) {
            throw new InvalidInputException("movie.talents.invalid");
        }
    }
}
