package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
import com.mms.mms_api.dto.MovieDto;
import com.mms.mms_api.model.Movie;

@Mapper(config = DefaultMapperConfig.class,
        uses = { GenreMapper.class, LanguageMapper.class, StudioMapper.class, TalentMapper.class })
public interface MovieMapper {
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "studios", ignore = true)
    @Mapping(target = "talents", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    Movie toEntity(MovieCreateCommand command);

    MovieDto toDto(Movie movie);

    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "studios", ignore = true)
    @Mapping(target = "talents", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    void updateEntity(MovieUpdateCommand command, @MappingTarget Movie movie);
}
