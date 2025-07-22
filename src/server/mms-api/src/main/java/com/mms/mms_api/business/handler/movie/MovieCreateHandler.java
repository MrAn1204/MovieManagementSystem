package com.mms.mms_api.business.handler.movie;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.dto.MovieDto;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.util.mapper.MovieMapper;

public class MovieCreateHandler extends MovieBaseHandler<MovieCreateCommand, MovieDto> {
    public MovieCreateHandler(
        MovieCreateCommand request,
        MovieMapper movieMapper,
        MovieRepository movieRepository
    ) {
        super(request, movieMapper, movieRepository);
        this.movieRepository = movieRepository;
    }

    public MovieDto execute() {
        Movie movie = movieMapper.toEntity(request);

        Movie savedMovie = movieRepository.save(movie);

        return movieMapper.toDto(savedMovie);
    }
}
