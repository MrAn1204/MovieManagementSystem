package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.PromotionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PromotionValidationService {
    private final PromotionRepository promotionRepository;

    public boolean existsById(@NonNull UUID id) {
        return promotionRepository.existsById(id);
    }
}
