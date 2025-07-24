package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
import com.mms.mms_api.dto.MovieDto;
import com.mms.mms_api.model.Genre;
import com.mms.mms_api.model.Language;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Studio;
import com.mms.mms_api.model.Talent;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovieMapper {
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "studios", ignore = true)
    @Mapping(target = "talents", ignore = true)
    Movie toEntity(MovieCreateCommand command);

    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "studios", ignore = true)
    @Mapping(target = "talents", ignore = true)
    void updateEntity(MovieUpdateCommand command, @MappingTarget Movie movie);

    MovieDto toDto(Movie movie);

    default String genreToString(Genre genre) {
        return genre != null ? genre.getName() : null;
    }

    default String languageToString(Language language) {
        return language != null ? language.getName() : null;
    }

    default String studioToString(Studio studio) {
        return studio != null ? studio.getName() : null;
    }

    default String talentToString(Talent talent) {
        return talent != null ? talent.getName() : null;
    }
}
