package com.mms.mms_api.business.handler.talent;
import com.mms.mms_api.util.mapper.TalentMapper;
import com.mms.mms_api.data.TalentRepository;
import com.mms.mms_api.dto.movie.TalentDto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.talent.TalentGetAllQuery;

@Component
public class TalentGetAllHandler extends TalentBaseHandler<TalentGetAllQuery, List<TalentDto>> {
    private final TalentMapper talentMapper;
    private final TalentRepository talentRepository;

    public TalentGetAllHandler(TalentMapper talentMapper, TalentRepository talentRepository) {
        super();
        this.talentMapper = talentMapper;
        this.talentRepository = talentRepository;
    }

    @Override
    public List<TalentDto> execute(TalentGetAllQuery request) {
        return talentRepository.findAll().stream()
            .map(talentMapper::toDto)
            .toList();
    }
}
