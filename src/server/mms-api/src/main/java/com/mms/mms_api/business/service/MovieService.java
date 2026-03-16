package com.mms.mms_api.business.service;

import java.util.List;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.business.command.movie.MovieDeleteCommand;
import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
import com.mms.mms_api.business.handler.movie.MovieCreateHandler;
import com.mms.mms_api.business.handler.movie.MovieDeleteHandler;
import com.mms.mms_api.business.handler.movie.MovieGetAllHandler;
import com.mms.mms_api.business.handler.movie.MovieGetByIdHandler;
import com.mms.mms_api.business.handler.movie.MovieSearchHandler;
import com.mms.mms_api.business.handler.movie.MovieUpdateHandler;
import com.mms.mms_api.business.query.movie.MovieGetAllQuery;
import com.mms.mms_api.business.query.movie.MovieGetByIdQuery;
import com.mms.mms_api.business.query.movie.MovieSearchQuery;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.GenreRepository;
import com.mms.mms_api.data.LanguageRepository;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.data.StudioRepository;
import com.mms.mms_api.data.TalentRepository;
import com.mms.mms_api.dto.movie.MovieDto;
import com.mms.mms_api.util.mapper.MovieMapper;

import lombok.AllArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    private final GenreRepository genreRepository;

    private final LanguageRepository languageRepository;

    private final StudioRepository studioRepository;

    private final TalentRepository talentRepository;

    private final MovieMapper movieMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    public MovieDto handle(MovieCreateCommand request) {
        MovieCreateHandler handler = new MovieCreateHandler(request, movieMapper, movieRepository,
                genreRepository, languageRepository, studioRepository, talentRepository);
        return handler.execute();
    }

    public List<MovieDto> handle(MovieGetAllQuery request) {
        MovieGetAllHandler handler = new MovieGetAllHandler(request, movieMapper, movieRepository);
        return handler.execute();
    }

    public MovieDto handle(MovieGetByIdQuery request) {
        MovieGetByIdHandler handler = new MovieGetByIdHandler(request, movieMapper, movieRepository);
        return handler.execute();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public MovieDto handle(MovieUpdateCommand request) {
        MovieUpdateHandler handler = new MovieUpdateHandler(request, movieMapper, movieRepository,
                genreRepository, languageRepository, studioRepository, talentRepository);
        return handler.execute();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void handle(MovieDeleteCommand request) {
        MovieDeleteHandler handler = new MovieDeleteHandler(request, movieRepository);
        handler.execute();
    }

    public PaginatedResult<MovieDto> handle(MovieSearchQuery request) {
        MovieSearchHandler handler = new MovieSearchHandler(request, movieMapper, movieRepository);
        return handler.execute();
    }
}