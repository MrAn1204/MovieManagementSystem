package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.LanguageRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LanguageValidationService {
    private final LanguageRepository languageRepository;

    public boolean existsById(@NonNull UUID id) {
        return languageRepository.existsById(id);
    }
}
