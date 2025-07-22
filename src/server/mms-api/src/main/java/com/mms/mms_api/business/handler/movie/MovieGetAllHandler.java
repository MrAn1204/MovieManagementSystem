package com.mms.mms_api.business.handler.movie;

import java.util.List;

import com.mms.mms_api.business.query.movie.MovieGetAllQuery;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.dto.MovieDto;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.util.mapper.MovieMapper;

public class MovieGetAllHandler extends MovieBaseHandler<MovieGetAllQuery, List<MovieDto>> {

    public MovieGetAllHandler(MovieGetAllQuery request, MovieMapper movieMapper, MovieRepository movieRepository) {
        super(request, movieMapper, movieRepository);
    }

    @Override
    public List<MovieDto> execute() {
        List<Movie> movies = movieRepository.findAll();

        return movies.stream().map(movieMapper::toDto).toList();
    }

}
