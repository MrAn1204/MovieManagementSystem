package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.InvoiceRepository;

import lombok.AllArgsConstructor;

/**
 * Validation helper for invoice ownership and existence checks.
 */
@Service("invoiceValidationService")
@AllArgsConstructor
public class InvoiceValidationService {
    private final InvoiceRepository invoiceRepository;

    /**
     * Checks whether an invoice exists by id.
     *
     * @param id invoice identifier
     * @return true when the invoice exists
     */
    public boolean existsById(@NonNull UUID id) {
        return invoiceRepository.existsById(id);
    }

    /**
     * Checks whether an invoice belongs to a user.
     *
     * @param invoiceId invoice identifier
     * @param userId user identifier
     * @return true when owned by the user
     */
    public boolean isOwnedByUserId(@NonNull UUID invoiceId, UUID userId) {
        return invoiceRepository.findById(invoiceId)
                .map(invoice -> invoice.getUser().getId().equals(userId))
                .orElse(false);
    }

    /**
     * Checks whether invoice owner has sufficient score for score usage.
     *
     * @param invoiceId invoice identifier
     * @param useScore score amount to consume
     * @return true when user score is sufficient
     */
    public boolean hasUserSufficientScore(@NonNull UUID invoiceId, int useScore) {
        return invoiceRepository.findById(invoiceId)
                .map(invoice -> invoice.getUser().getScore() >= useScore)
                .orElse(false);
    }
}
