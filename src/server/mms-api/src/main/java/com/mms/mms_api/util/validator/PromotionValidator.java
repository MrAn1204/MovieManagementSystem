package com.mms.mms_api.util.validator;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.promotion.PromotionUpdateCommand;
import com.mms.mms_api.business.service.validation.PromotionValidationService;
import com.mms.mms_api.exception.ErrorLinkedList;
import com.mms.mms_api.exception.ErrorType;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PromotionValidator implements BaseValidator {
    private final PromotionValidationService promotionValidationService;

    public void validate(PromotionUpdateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);
    }

    private void validateId(ErrorLinkedList errors, @NonNull UUID id) {
        if (!promotionValidationService.existsById(id)) {
            errors.add("id", "promotion.notFound");
        }
    }
}
