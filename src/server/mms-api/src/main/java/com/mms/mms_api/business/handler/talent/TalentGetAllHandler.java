package com.mms.mms_api.business.handler.talent;
import com.mms.mms_api.util.mapper.TalentMapper;
import com.mms.mms_api.data.TalentRepository;
import com.mms.mms_api.dto.movie.TalentDto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.talent.TalentGetAllQuery;

/**
 * Handles requests to retrieve all talents.
 */
@Component
public class TalentGetAllHandler extends TalentBaseHandler<TalentGetAllQuery, List<TalentDto>> {
    private final TalentMapper talentMapper;
    private final TalentRepository talentRepository;

    /**
     * Creates a TalentGetAllHandler.
     *
     * @param talentMapper talent mapper
     * @param talentRepository talent repository
     */
    public TalentGetAllHandler(TalentMapper talentMapper, TalentRepository talentRepository) {
        super();
        this.talentMapper = talentMapper;
        this.talentRepository = talentRepository;
    }

    /**
     * Retrieves all talents.
     *
     * @param request query object
     * @return list of talent DTOs
     */
    @Override
    public List<TalentDto> execute(TalentGetAllQuery request) {
        return talentRepository.findAll().stream()
            .map(talentMapper::toDto)
            .toList();
    }
}
