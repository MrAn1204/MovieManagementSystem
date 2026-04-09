package com.mms.mms_api.business.handler.language;
import com.mms.mms_api.util.mapper.LanguageMapper;
import com.mms.mms_api.data.LanguageRepository;
import com.mms.mms_api.dto.movie.LanguageDto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.language.LanguageGetAllQuery;

@Component
public class LanguageGetAllHandler extends LanguageBaseHandler<LanguageGetAllQuery, List<LanguageDto>> {
    private final LanguageMapper languageMapper;
    private final LanguageRepository languageRepository;

    public LanguageGetAllHandler(LanguageMapper languageMapper, LanguageRepository languageRepository) {
        super();
        this.languageMapper = languageMapper;
        this.languageRepository = languageRepository;
    }

    @Override
    public List<LanguageDto> execute(LanguageGetAllQuery request) {
        return languageRepository.findAll().stream()
            .map(languageMapper::toDto)
            .toList();
    }
}
