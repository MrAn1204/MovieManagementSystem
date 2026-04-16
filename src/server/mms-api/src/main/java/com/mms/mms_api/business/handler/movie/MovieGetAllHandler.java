package com.mms.mms_api.business.handler.movie;

import org.springframework.stereotype.Component;

import java.util.List;

import com.mms.mms_api.business.query.movie.MovieGetAllQuery;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.dto.movie.MovieDto;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.util.mapper.MovieMapper;

/**
 * Handles requests to retrieve all movies.
 */
@Component
public class MovieGetAllHandler extends MovieBaseHandler<MovieGetAllQuery, List<MovieDto>> {

    /**
     * Creates a MovieGetAllHandler.
     *
     * @param movieMapper movie mapper
     * @param movieRepository movie repository
     */
    public MovieGetAllHandler(MovieMapper movieMapper, MovieRepository movieRepository) {
        super(movieMapper, movieRepository);
    }

    /**
     * Retrieves all movies.
     *
     * @param request query object
     * @return list of movie DTOs
     */
    @Override
    public List<MovieDto> execute(MovieGetAllQuery request) {
        List<Movie> movies = movieRepository.findAll();

        return movies.stream().map(movieMapper::toDto).toList();
    }

}
