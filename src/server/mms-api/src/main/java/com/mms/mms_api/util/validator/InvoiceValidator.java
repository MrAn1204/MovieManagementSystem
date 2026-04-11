package com.mms.mms_api.util.validator;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.mms.mms_api.business.command.invoice.InvoiceCreateCommand;
import com.mms.mms_api.business.command.invoice.InvoiceUpdateCommand;
import com.mms.mms_api.business.service.validation.InvoiceValidationService;
import com.mms.mms_api.business.service.validation.TicketValidationService;
import com.mms.mms_api.business.service.validation.UserValidationService;
import com.mms.mms_api.exception.ErrorSet;
import com.mms.mms_api.exception.ErrorType;

import lombok.AllArgsConstructor;

/**
 * Validates invoice create and update commands.
 */
@Component
@AllArgsConstructor
public class InvoiceValidator implements BaseValidator {
    private final InvoiceValidationService invoiceValidationService;

    private final TicketValidationService ticketValidationService;

    private final UserValidationService userValidationService;

    public void validate(InvoiceCreateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateTickets(errors, command.getTicketIds());
        validateUser(errors, command.getUserId());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    public void validate(InvoiceUpdateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateTickets(errors, command.getTicketIds());
        validateScore(errors, command.getId(), command.getUseScore());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    private void validateId(ErrorSet errors, @NonNull UUID id) {
        if (!invoiceValidationService.existsById(id)) {
            errors.add("id", "invoice.notFound");
        }
    }

    private void validateTickets(ErrorSet errors, List<UUID> ticketIds) {
        if (CollectionUtils.isEmpty(ticketIds)) {
            return;
        }

        if (!ticketValidationService.existsAllByIdIn(ticketIds)) {
            errors.add("tickets", "invoice.tickets.invalid");
        }
    }

    private void validateUser(ErrorSet errors, @NonNull UUID userId) {
        if (!userValidationService.existsById(userId)) {
            errors.add("user", "user.notFound");
        }
    }

    private void validateScore(ErrorSet errors, @NonNull UUID invoiceId, int useScore) {
        if (!invoiceValidationService.hasUserSufficientScore(invoiceId, useScore)) {
            errors.add("useScore", "user.score.insufficient");
        }
    }
}
