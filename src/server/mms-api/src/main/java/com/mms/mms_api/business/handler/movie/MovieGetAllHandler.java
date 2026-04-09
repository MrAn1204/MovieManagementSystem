package com.mms.mms_api.business.handler.movie;

import org.springframework.stereotype.Component;

import java.util.List;

import com.mms.mms_api.business.query.movie.MovieGetAllQuery;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.dto.movie.MovieDto;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.util.mapper.MovieMapper;

@Component
public class MovieGetAllHandler extends MovieBaseHandler<MovieGetAllQuery, List<MovieDto>> {

    public MovieGetAllHandler(MovieMapper movieMapper, MovieRepository movieRepository) {
        super(movieMapper, movieRepository);
    }

    @Override
    public List<MovieDto> execute(MovieGetAllQuery request) {
        List<Movie> movies = movieRepository.findAll();

        return movies.stream().map(movieMapper::toDto).toList();
    }

}
