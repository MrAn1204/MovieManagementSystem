package com.mms.mms_api.util.validator;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.mms.mms_api.business.command.promotion.PromotionCreateCommand;
import com.mms.mms_api.business.command.promotion.PromotionUpdateCommand;
import com.mms.mms_api.business.service.validation.PromotionValidationService;
import com.mms.mms_api.business.service.validation.TicketValidationService;
import com.mms.mms_api.exception.ErrorLinkedList;
import com.mms.mms_api.exception.ErrorType;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PromotionValidator implements BaseValidator {
    private final PromotionValidationService promotionValidationService;

    private final TicketValidationService ticketValidationService;

    public void validate(PromotionCreateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateTickets(errors, command.getTicketIds());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    public void validate(PromotionUpdateCommand command) {
        ErrorLinkedList errors = new ErrorLinkedList();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateTickets(errors, command.getTicketIds());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    private void validateId(ErrorLinkedList errors, @NonNull UUID id) {
        if (!promotionValidationService.existsById(id)) {
            errors.add("id", "promotion.notFound");
        }
    }

    private void validateTickets(ErrorLinkedList errors, List<UUID> ticketIds) {
        if (CollectionUtils.isEmpty(ticketIds)) {
            return;
        }

        if (!ticketValidationService.existsAllByIdIn(ticketIds)) {
            errors.add("tickets", "promotion.tickets.invalid");
        }
    }
}
