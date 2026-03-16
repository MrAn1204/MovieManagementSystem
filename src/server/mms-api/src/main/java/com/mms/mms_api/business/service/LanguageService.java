package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.handler.language.LanguageGetAllHandler;
import com.mms.mms_api.business.query.language.LanguageGetAllQuery;
import com.mms.mms_api.data.LanguageRepository;
import com.mms.mms_api.dto.movie.LanguageDto;
import com.mms.mms_api.util.mapper.LanguageMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;
    
    private final LanguageMapper languageMapper;

    public List<LanguageDto> handle(LanguageGetAllQuery request) {
        LanguageGetAllHandler handler = new LanguageGetAllHandler(request, languageMapper, languageRepository);
        return handler.execute();
    }
}
