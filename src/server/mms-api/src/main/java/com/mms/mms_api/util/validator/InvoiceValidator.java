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

    /**
     * Validates an invoice create command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>All referenced tickets exist, if provided.</li>
     *   <li>The referenced user exists.</li>
     * </ul>
     *
     * @param command the invoice create command to validate
     * @throws com.mms.mms_api.exception.InvalidInputException if any ticket reference is invalid or the user is not found
     */
    public void validate(InvoiceCreateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateTickets(errors, command.getTicketIds());
        validateUser(errors, command.getUserId());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    /**
     * Validates an invoice update command.
     *
     * <p>Performs the following validations:
     * <ul>
     *   <li>An invoice with the given id exists.</li>
     *   <li>All referenced tickets exist, if provided.</li>
     *   <li>The user associated with the invoice has sufficient score for the requested redemption.</li>
     * </ul>
     *
     * @param command the invoice update command to validate
     * @throws com.mms.mms_api.exception.ResourceNotFoundException if the invoice is not found
     * @throws com.mms.mms_api.exception.InvalidInputException if any ticket reference is invalid or the score is insufficient
     */
    public void validate(InvoiceUpdateCommand command) {
        ErrorSet errors = new ErrorSet();

        validateId(errors, command.getId());

        errors.throwIfNotEmpty(ErrorType.RESOURCE_NOT_FOUND);

        validateTickets(errors, command.getTicketIds());
        validateScore(errors, command.getId(), command.getUseScore());

        errors.throwIfNotEmpty(ErrorType.INVALID_INPUT);
    }

    /**
     * Checks that an invoice with the given id exists and adds an error if not.
     *
     * @param errors the error accumulator
     * @param id the invoice id to look up
     */
    private void validateId(ErrorSet errors, @NonNull UUID id) {
        if (!invoiceValidationService.existsById(id)) {
            errors.add("id", "invoice.notFound");
        }
    }

    /**
     * Checks that all tickets in the given id list exist and adds an error if not.
     * Skips validation if the list is null or empty.
     *
     * @param errors the error accumulator
     * @param ticketIds the list of ticket ids to validate
     */
    private void validateTickets(ErrorSet errors, List<UUID> ticketIds) {
        if (CollectionUtils.isEmpty(ticketIds)) {
            return;
        }

        if (!ticketValidationService.existsAllByIdIn(ticketIds)) {
            errors.add("tickets", "invoice.tickets.invalid");
        }
    }

    /**
     * Checks that the user with the given id exists and adds an error if not.
     *
     * @param errors the error accumulator
     * @param userId the user id to look up
     */
    private void validateUser(ErrorSet errors, @NonNull UUID userId) {
        if (!userValidationService.existsById(userId)) {
            errors.add("user", "user.notFound");
        }
    }

    /**
     * Checks that the invoice's associated user has enough score to redeem the requested amount.
     *
     * @param errors the error accumulator
     * @param invoiceId the invoice id used to look up the user's current score
     * @param useScore the score amount the user wants to redeem
     */
    private void validateScore(ErrorSet errors, @NonNull UUID invoiceId, int useScore) {
        if (!invoiceValidationService.hasUserSufficientScore(invoiceId, useScore)) {
            errors.add("useScore", "user.score.insufficient");
        }
    }
}
