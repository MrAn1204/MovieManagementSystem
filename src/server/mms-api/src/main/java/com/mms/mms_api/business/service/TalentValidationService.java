package com.mms.mms_api.business.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mms.mms_api.data.TalentRepository;

import lombok.AllArgsConstructor;

/**
 * Validation helper for talent existence checks.
 */
@Service
@AllArgsConstructor
public class TalentValidationService {
    private final TalentRepository talentRepository;

    /**
     * Checks whether all given talent ids exist.
     *
     * @param ids talent identifiers
     * @return true when all identifiers exist
     */
    public boolean existsAllById(List<UUID> ids) {
        return talentRepository.existsAllByIdIn(ids);
    }
}
