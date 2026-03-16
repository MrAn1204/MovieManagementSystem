package com.mms.mms_api.business.handler.movie;

import com.mms.mms_api.business.query.movie.MovieGetByIdQuery;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.dto.movie.MovieDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.util.mapper.MovieMapper;

public class MovieGetByIdHandler extends MovieBaseHandler<MovieGetByIdQuery, MovieDto> {
    public MovieGetByIdHandler(MovieGetByIdQuery request, MovieMapper movieMapper, MovieRepository movieRepository) {
        super(request, movieMapper, movieRepository);
    }

    @Override
    public MovieDto execute() {
        Movie movie = movieRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("movie.notFound"));

        return movieMapper.toDto(movie);
    }

}
