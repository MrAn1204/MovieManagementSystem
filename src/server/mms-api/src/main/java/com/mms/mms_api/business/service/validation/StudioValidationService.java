package com.mms.mms_api.business.service.validation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mms.mms_api.data.StudioRepository;

import lombok.AllArgsConstructor;

/**
 * Validation helper for studio existence checks.
 */
@Service
@AllArgsConstructor
public class StudioValidationService {
    private final StudioRepository studioRepository;

    /**
     * Checks whether all given studio ids exist.
     *
     * @param ids studio identifiers
     * @return true when all identifiers exist
     */
    public boolean existsAllById(List<UUID> ids) {
        return studioRepository.existsAllByIdIn(ids);
    }
}
