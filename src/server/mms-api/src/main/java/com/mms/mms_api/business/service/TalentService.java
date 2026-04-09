package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.query.talent.TalentGetAllQuery;
import com.mms.mms_api.dto.movie.TalentDto;
import com.mms.mms_api.mediator.RequestMediator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TalentService {
    private final RequestMediator mediator;

    public List<TalentDto> handle(TalentGetAllQuery request) {
        return mediator.execute(request);
    }
}
