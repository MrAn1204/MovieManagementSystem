package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.handler.talent.TalentGetAllHandler;
import com.mms.mms_api.business.query.talent.TalentGetAllQuery;
import com.mms.mms_api.data.TalentRepository;
import com.mms.mms_api.dto.movie.TalentDto;
import com.mms.mms_api.util.mapper.TalentMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TalentService {
    private final TalentRepository talentRepository;
    
    private final TalentMapper talentMapper;

    public List<TalentDto> handle(TalentGetAllQuery request) {
        TalentGetAllHandler handler = new TalentGetAllHandler(request, talentMapper, talentRepository);
        return handler.execute();
    }
}
