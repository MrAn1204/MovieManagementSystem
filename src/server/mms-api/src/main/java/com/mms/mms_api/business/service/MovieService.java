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
import lombok.AllArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {
    private final RequestMediator mediator;

    @PreAuthorize("hasAuthority('ADMIN')")
    public MovieDto handle(MovieCreateCommand request) {
        return mediator.execute(request);
    }

    public List<MovieDto> handle(MovieGetAllQuery request) {
        return mediator.execute(request);
    }

    public MovieDto handle(MovieGetByIdQuery request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public MovieDto handle(MovieUpdateCommand request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void handle(MovieDeleteCommand request) {
        mediator.execute(request);
    }

    public PaginatedResult<MovieDto> handle(MovieSearchQuery request) {
        return mediator.execute(request);
    }
}
