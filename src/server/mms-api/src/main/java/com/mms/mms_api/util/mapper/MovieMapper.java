package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
import com.mms.mms_api.business.service.GscService;
import com.mms.mms_api.dto.movie.MovieDto;
import com.mms.mms_api.model.Movie;

@Mapper(config = DefaultMapperConfig.class, uses = { GenreMapper.class, LanguageMapper.class, StudioMapper.class,
        TalentMapper.class })
public abstract class MovieMapper {
    protected GscService gscService;

    @Autowired
    protected void setGscService(GscService gscService) {
        this.gscService = gscService;
    }

    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "studios", ignore = true)
    @Mapping(target = "talents", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "thumbnail", ignore = true)
    public abstract Movie toEntity(MovieCreateCommand command);

    @Mapping(target = "thumbnail", qualifiedByName = "getThumbnailUrl")
    public abstract MovieDto toDto(Movie movie);

    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "studios", ignore = true)
    @Mapping(target = "talents", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "thumbnail", ignore = true)
    public abstract void updateEntity(MovieUpdateCommand command, @MappingTarget Movie movie);

    @Named("getThumbnailUrl")
    protected String getThumbnailUrl(String thumbnail) {
        if (thumbnail == null || thumbnail.isEmpty()) {
            return null;
        }

        return gscService.getPublicUrl(thumbnail);
    }
}
