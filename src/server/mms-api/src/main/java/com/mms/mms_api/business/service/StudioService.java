package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.query.studio.StudioGetAllQuery;
import com.mms.mms_api.dto.movie.StudioDto;
import com.mms.mms_api.mediator.RequestMediator;

import lombok.AllArgsConstructor;

/**
 * Provides read operations for studios.
 */
@Service
@AllArgsConstructor
public class StudioService {
    private final RequestMediator mediator;

    /**
     * Returns all studios.
     *
     * @param request get-all query
     * @return list of studio DTOs
     */
    public List<StudioDto> handle(StudioGetAllQuery request) {
        return mediator.execute(request);
    }
}
