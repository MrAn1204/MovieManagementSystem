package com.mms.mms_api.business.handler.studio;
import com.mms.mms_api.util.mapper.StudioMapper;
import com.mms.mms_api.data.StudioRepository;
import com.mms.mms_api.dto.movie.StudioDto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.studio.StudioGetAllQuery;

/**
 * Handles requests to retrieve all studios.
 */
@Component
public class StudioGetAllHandler extends StudioBaseHandler<StudioGetAllQuery, List<StudioDto>> {
    private final StudioMapper studioMapper;
    private final StudioRepository studioRepository;

    /**
     * Creates a StudioGetAllHandler.
     *
     * @param studioMapper studio mapper
     * @param studioRepository studio repository
     */
    public StudioGetAllHandler(StudioMapper studioMapper, StudioRepository studioRepository) {
        super();
        this.studioMapper = studioMapper;
        this.studioRepository = studioRepository;
    }

    /**
     * Retrieves all studios.
     *
     * @param request query object
     * @return list of studio DTOs
     */
    @Override
    public List<StudioDto> execute(StudioGetAllQuery request) {
        return studioRepository.findAll().stream()
            .map(studioMapper::toDto)
            .toList();
    }
}
