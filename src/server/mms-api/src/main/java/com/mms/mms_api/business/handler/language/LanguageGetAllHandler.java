package com.mms.mms_api.business.handler.language;
import com.mms.mms_api.util.mapper.LanguageMapper;
import com.mms.mms_api.data.LanguageRepository;
import com.mms.mms_api.dto.movie.LanguageDto;

import java.util.List;
import com.mms.mms_api.business.query.language.LanguageGetAllQuery;

public class LanguageGetAllHandler extends LanguageBaseHandler<LanguageGetAllQuery, List<LanguageDto>> {
    private final LanguageMapper languageMapper;
    private final LanguageRepository languageRepository;

    public LanguageGetAllHandler(LanguageGetAllQuery request, LanguageMapper languageMapper, LanguageRepository languageRepository) {
        super(request);
        this.languageMapper = languageMapper;
        this.languageRepository = languageRepository;
    }

    @Override
    public List<LanguageDto> execute() {
        return languageRepository.findAll().stream()
            .map(languageMapper::toDto)
            .toList();
    }
}
