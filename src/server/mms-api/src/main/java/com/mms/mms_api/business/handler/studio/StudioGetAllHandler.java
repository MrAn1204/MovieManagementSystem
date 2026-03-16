package com.mms.mms_api.business.handler.studio;
import com.mms.mms_api.util.mapper.StudioMapper;
import com.mms.mms_api.data.StudioRepository;
import com.mms.mms_api.dto.movie.StudioDto;

import java.util.List;
import com.mms.mms_api.business.query.studio.StudioGetAllQuery;

public class StudioGetAllHandler extends StudioBaseHandler<StudioGetAllQuery, List<StudioDto>> {
    private final StudioMapper studioMapper;
    private final StudioRepository studioRepository;

    public StudioGetAllHandler(StudioGetAllQuery request, StudioMapper studioMapper, StudioRepository studioRepository) {
        super(request);
        this.studioMapper = studioMapper;
        this.studioRepository = studioRepository;
    }

    @Override
    public List<StudioDto> execute() {
        return studioRepository.findAll().stream()
            .map(studioMapper::toDto)
            .toList();
    }
}
