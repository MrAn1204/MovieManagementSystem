package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.PromotionRepository;

import lombok.AllArgsConstructor;

/**
 * Validation helper for promotion existence checks.
 */
@Service
@AllArgsConstructor
public class PromotionValidationService {
    private final PromotionRepository promotionRepository;

    /**
     * Checks whether a promotion exists by id.
     *
     * @param id promotion identifier
     * @return true when the promotion exists
     */
    public boolean existsById(@NonNull UUID id) {
        return promotionRepository.existsById(id);
    }
}
