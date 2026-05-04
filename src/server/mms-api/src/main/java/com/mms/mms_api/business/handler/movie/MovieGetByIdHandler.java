package com.mms.mms_api.business.handler.movie;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.movie.MovieGetByIdQuery;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.dto.movie.MovieDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.util.mapper.MovieMapper;

/**
 * Handles requests to retrieve movie by id.
 */
@Component
public class MovieGetByIdHandler extends MovieBaseHandler<MovieGetByIdQuery, MovieDetailDto> {
    /**
     * Creates a MovieGetByIdHandler.
     *
     * @param movieMapper movie mapper
     * @param movieRepository movie repository
     */
    public MovieGetByIdHandler(MovieMapper movieMapper, MovieRepository movieRepository) {
        super(movieMapper, movieRepository);
    }

    /**
     * Retrieves a movie by its identifier.
     *
     * @param request query containing the target movie id
     * @return movie DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the movie does not exist
     */
    @Override
    public MovieDetailDto execute(MovieGetByIdQuery request) {
        Movie movie = movieRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("movie.notFound"));

        return movieMapper.toDetailDto(movie);
    }

}
