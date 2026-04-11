package com.mms.mms_api.business.service;

import java.util.List;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.business.command.movie.MovieDeleteCommand;
import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
import com.mms.mms_api.business.query.movie.MovieGetAllQuery;
import com.mms.mms_api.business.query.movie.MovieGetByIdQuery;
import com.mms.mms_api.business.query.movie.MovieSearchQuery;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.movie.MovieDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.MovieValidator;
import lombok.AllArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * Provides CRUD and search operations for movies.
 */
@Service
@AllArgsConstructor
public class MovieService {
    private final RequestMediator mediator;
    private final MovieValidator movieValidator;

    /**
     * Creates a movie.
     *
     * @param request create command
     * @return created movie DTO
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public MovieDto handle(MovieCreateCommand request) {
        movieValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Returns all movies.
     *
     * @param request get-all query
     * @return list of movie DTOs
     */
    public List<MovieDto> handle(MovieGetAllQuery request) {
        return mediator.execute(request);
    }

    /**
     * Returns movie details by id.
     *
     * @param request get-by-id query
     * @return movie DTO
     */
    public MovieDto handle(MovieGetByIdQuery request) {
        return mediator.execute(request);
    }

    /**
     * Updates a movie.
     *
     * @param request update command
     * @return updated movie DTO
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public MovieDto handle(MovieUpdateCommand request) {
        movieValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Deletes a movie.
     *
     * @param request delete command
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void handle(MovieDeleteCommand request) {
        mediator.execute(request);
    }

    /**
     * Searches movies with pagination.
     *
     * @param request search query
     * @return paginated movie result
     */
    public PaginatedResult<MovieDto> handle(MovieSearchQuery request) {
        return mediator.execute(request);
    }
}
