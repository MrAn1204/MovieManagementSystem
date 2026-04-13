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

    /**
     * Validates a promotion update command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>A promotion with the given id exists.</li>
     * </ul>
     *
     * @param command the promotion update command to validate
     * @throws com.mms.mms_api.exception.ResourceNotFoundException if the promotion is not found
     */
    public void validate(PromotionUpdateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);
    }

    /**
     * Checks that a promotion with the given id exists and adds an error if not.
     *
     * @param errors the error accumulator
     * @param id the promotion id to look up
     */
    private void validateId(ErrorSet errors, @NonNull UUID id) {
        if (!promotionValidationService.existsById(id)) {
            errors.add("id", "promotion.notFound");
        }
    }
}
