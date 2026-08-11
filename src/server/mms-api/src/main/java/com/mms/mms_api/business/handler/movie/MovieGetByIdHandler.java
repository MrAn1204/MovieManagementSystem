package com.mms.mms_api.business.handler.movie;

import com.mms.mms_api.util.CurrentUserHelper;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.movie.MovieGetByIdQuery;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.dto.AuditDto;
import com.mms.mms_api.dto.movie.MovieDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.util.mapper.MovieMapper;

/**
 * Handles requests to retrieve movie by id.
 */
@Component
public class MovieGetByIdHandler extends MovieBaseHandler<MovieGetByIdQuery, MovieDetailDto> {
    private final CurrentUserHelper currentUser;

    /**
     * Creates a MovieGetByIdHandler.
     *
     * @param movieMapper movie mapper
     * @param movieRepository movie repository
     * @param currentUserHelper current user helper
     */
    public MovieGetByIdHandler(MovieMapper movieMapper, MovieRepository movieRepository, CurrentUserHelper currentUserHelper) {
        super(movieMapper, movieRepository);
        this.currentUser = currentUserHelper;
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

        MovieDetailDto dto = movieMapper.toDetailDto(movie);

        if (currentUser.isAdmin()) {
            dto.setAudit(new AuditDto(movie));
        }

        return dto;
    }

}
