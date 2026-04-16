package com.mms.mms_api.business.handler.language;
import com.mms.mms_api.util.mapper.LanguageMapper;
import com.mms.mms_api.data.LanguageRepository;
import com.mms.mms_api.dto.movie.LanguageDto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.language.LanguageGetAllQuery;

/**
 * Handles requests to retrieve all languages.
 */
@Component
public class LanguageGetAllHandler extends LanguageBaseHandler<LanguageGetAllQuery, List<LanguageDto>> {
    private final LanguageMapper languageMapper;
    private final LanguageRepository languageRepository;

    /**
     * Creates a LanguageGetAllHandler.
     *
     * @param languageMapper language mapper
     * @param languageRepository language repository
     */
    public LanguageGetAllHandler(LanguageMapper languageMapper, LanguageRepository languageRepository) {
        super();
        this.languageMapper = languageMapper;
        this.languageRepository = languageRepository;
    }

    /**
     * Retrieves all languages.
     *
     * @param request query object
     * @return list of language DTOs
     */
    @Override
    public List<LanguageDto> execute(LanguageGetAllQuery request) {
        return languageRepository.findAll().stream()
            .map(languageMapper::toDto)
            .toList();
    }
}
