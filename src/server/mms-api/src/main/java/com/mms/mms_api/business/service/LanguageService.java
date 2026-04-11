package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.query.language.LanguageGetAllQuery;
import com.mms.mms_api.dto.movie.LanguageDto;
import com.mms.mms_api.mediator.RequestMediator;

import lombok.AllArgsConstructor;

/**
 * Provides read operations for languages.
 */
@Service
@AllArgsConstructor
public class LanguageService {
    private final RequestMediator mediator;

    /**
     * Returns all languages.
     *
     * @param request get-all query
     * @return list of language DTOs
     */
    public List<LanguageDto> handle(LanguageGetAllQuery request) {
        return mediator.execute(request);
    }
}
