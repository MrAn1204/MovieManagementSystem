package com.mms.mms_api.business.handler.movie;

import org.springframework.stereotype.Component;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.mms.mms_api.business.query.movie.MovieSearchQuery;
import com.mms.mms_api.business.specification.MovieSpecification;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.dto.movie.MovieDto;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.util.SearchHelper;
import com.mms.mms_api.util.mapper.MovieMapper;

/**
 * Handles movie search queries.
 */
@Component
public class MovieSearchHandler extends MovieBaseHandler<MovieSearchQuery, PaginatedResult<MovieDto>> {

    /**
     * Creates a MovieSearchHandler.
     *
     * @param movieMapper movie mapper
     * @param movieRepository movie repository
     */
    public MovieSearchHandler(MovieMapper movieMapper, MovieRepository movieRepository) {
        super(movieMapper, movieRepository);
    }

    /**
     * Executes a paginated movie search using the supplied filters.
     *
     * @param request search query with filters and pagination parameters
     * @return paginated result of movie DTOs
     */
    @Override
    public PaginatedResult<MovieDto> execute(MovieSearchQuery request) {
        Pageable pageable = SearchHelper.generatePageable(request.getPageNumber(), request.getPageSize());

        Specification<Movie> spec = new MovieSpecification(request);

        Page<Movie> moviePage = movieRepository.findAll(spec, pageable);

        return SearchHelper.generatePaginatedResult(moviePage, movieMapper::toDto);
    }

}
