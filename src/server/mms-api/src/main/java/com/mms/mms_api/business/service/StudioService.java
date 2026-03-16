package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.handler.studio.StudioGetAllHandler;
import com.mms.mms_api.business.query.studio.StudioGetAllQuery;
import com.mms.mms_api.data.StudioRepository;
import com.mms.mms_api.dto.movie.StudioDto;
import com.mms.mms_api.util.mapper.StudioMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudioService {
    private final StudioRepository studioRepository;
    
    private final StudioMapper studioMapper;

    public List<StudioDto> handle(StudioGetAllQuery request) {
        StudioGetAllHandler handler = new StudioGetAllHandler(request, studioMapper, studioRepository);
        return handler.execute();
    }
}
