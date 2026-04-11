package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.LanguageRepository;

import lombok.AllArgsConstructor;

/**
 * Validation helper for language existence checks.
 */
@Service
@AllArgsConstructor
public class LanguageValidationService {
    private final LanguageRepository languageRepository;

    /**
     * Checks whether a language exists by id.
     *
     * @param id language identifier
     * @return true when the language exists
     */
    public boolean existsById(@NonNull UUID id) {
        return languageRepository.existsById(id);
    }
}
