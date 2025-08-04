package com.mms.mms_api.business.handler.movie;

import java.util.Optional;

import com.mms.mms_api.business.query.movie.MovieGetByIdQuery;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.dto.MovieDto;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.util.mapper.MovieMapper;

public class MovieGetByIdHandler extends MovieBaseHandler<MovieGetByIdQuery, MovieDto> {
    public MovieGetByIdHandler(MovieGetByIdQuery request, MovieMapper movieMapper, MovieRepository movieRepository) {
        super(request, movieMapper, movieRepository);
    }

    @Override
    public MovieDto execute() {
        Optional<Movie> movieOptional = movieRepository.findById(request.getId());

        return movieOptional.map(movieMapper::toDto).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.MOVIE_NOT_FOUND.getValue()));
    }

}
