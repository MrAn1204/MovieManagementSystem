package com.mms.mms_api.util.validator;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.promotion.PromotionUpdateCommand;
import com.mms.mms_api.business.service.validation.PromotionValidationService;
import com.mms.mms_api.exception.ErrorSet;
import com.mms.mms_api.exception.ErrorType;

import lombok.AllArgsConstructor;

/**
 * Validates promotion update commands.
 */
@Component
@AllArgsConstructor
public class PromotionValidator implements BaseValidator {
    private final PromotionValidationService promotionValidationService;

    public void validate(PromotionUpdateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);
    }

    private void validateId(ErrorSet errors, @NonNull UUID id) {
        if (!promotionValidationService.existsById(id)) {
            errors.add("id", "promotion.notFound");
        }
    }
}
