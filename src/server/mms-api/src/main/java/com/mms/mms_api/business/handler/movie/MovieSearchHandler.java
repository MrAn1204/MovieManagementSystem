package com.mms.mms_api.business.handler.movie;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.mms.mms_api.business.query.movie.MovieSearchQuery;
import com.mms.mms_api.business.specification.MovieSpecification;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.dto.MovieDto;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.util.mapper.MovieMapper;

public class MovieSearchHandler extends MovieBaseHandler<MovieSearchQuery, PaginatedResult<MovieDto>> {

    public MovieSearchHandler(MovieSearchQuery request, MovieMapper movieMapper, MovieRepository movieRepository) {
        super(request, movieMapper, movieRepository);
    }

    @Override
    public PaginatedResult<MovieDto> execute() {
        Direction direction = Direction.fromString(request.getSortDirection().name());

        Sort sort = Sort.by(direction, request.getSortBy());

        Pageable pageable = PageRequest.of(request.getPageNumber() - 1, request.getPageSize(), sort);

        Specification<Movie> spec = new MovieSpecification(request);

        Page<Movie> moviePage = movieRepository.findAll(spec, pageable);

        List<MovieDto> movieDtos = moviePage.getContent().stream()
                .map(movieMapper::toDto)
                .toList();

        return new PaginatedResult<>(movieDtos, moviePage.getTotalElements(), moviePage.getTotalPages(),
                pageable.getPageSize(), pageable.getPageNumber() + 1);
    }

}
