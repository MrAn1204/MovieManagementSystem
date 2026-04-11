package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.query.genre.GenreGetAllQuery;
import com.mms.mms_api.dto.movie.GenreDto;
import com.mms.mms_api.mediator.RequestMediator;

import lombok.AllArgsConstructor;

/**
 * Provides read operations for genres.
 */
@Service
@AllArgsConstructor
public class GenreService {
    private final RequestMediator mediator;

    /**
     * Returns all genres.
     *
     * @param request get-all query
     * @return list of genre DTOs
     */
    public List<GenreDto> handle(GenreGetAllQuery request) {
        return mediator.execute(request);
    }
}
