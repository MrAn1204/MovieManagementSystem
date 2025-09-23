package com.mms.mms_api.util.validator;

import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Genre;
import com.mms.mms_api.model.Language;
import com.mms.mms_api.model.Studio;
import com.mms.mms_api.model.Talent;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

public class MovieValidator {
    private MovieValidator() {}

    public static void validateGenres(List<String> genreNames, List<Genre> mappedNames) {
        if (!CollectionUtils.isEmpty(genreNames) && genreNames.size() != mappedNames.size()) {
            throw new InvalidInputException("movie.genres.invalid");
        }
    }

    public static void validateLanguage(String languageName, Language mappedLanguage) {
        if (StringUtils.hasText(languageName) && mappedLanguage != null) {
            throw new InvalidInputException("movie.language.invalid");
        }
    }

    public static void validateStudios(List<String> studioNames, List<Studio> mappedStudios) {
        if (!CollectionUtils.isEmpty(studioNames) && studioNames.size() != mappedStudios.size()) {
            throw new InvalidInputException("movie.studios.invalid");
        }
    }

    public static void validateTalents(List<String> talentNames, List<Talent> mappedTalents) {
        if (!CollectionUtils.isEmpty(talentNames) && talentNames.size() != mappedTalents.size()) {
            throw new InvalidInputException("movie.talents.invalid");
        }
    }
}
