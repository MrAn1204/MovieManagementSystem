package com.mms.mms_api.business.handler.movie;

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

public class MovieSearchHandler extends MovieBaseHandler<MovieSearchQuery, PaginatedResult<MovieDto>> {

    public MovieSearchHandler(MovieSearchQuery request, MovieMapper movieMapper, MovieRepository movieRepository) {
        super(request, movieMapper, movieRepository);
    }

    @Override
    public PaginatedResult<MovieDto> execute() {
        Pageable pageable = SearchHelper.generatePageable(request.getPageNumber(), request.getPageSize());

        Specification<Movie> spec = new MovieSpecification(request);

        Page<Movie> moviePage = movieRepository.findAll(spec, pageable);

        return SearchHelper.generatePaginatedResult(moviePage, movieMapper::toDto);
    }

}
