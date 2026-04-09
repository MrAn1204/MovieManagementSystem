package com.mms.mms_api.business.handler.movie;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.movie.MovieGetByIdQuery;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.dto.movie.MovieDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.util.mapper.MovieMapper;

@Component
public class MovieGetByIdHandler extends MovieBaseHandler<MovieGetByIdQuery, MovieDto> {
    public MovieGetByIdHandler(MovieMapper movieMapper, MovieRepository movieRepository) {
        super(movieMapper, movieRepository);
    }

    @Override
    public MovieDto execute(MovieGetByIdQuery request) {
        Movie movie = movieRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("movie.notFound"));

        return movieMapper.toDto(movie);
    }

}
