package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.InvoiceRepository;

import lombok.AllArgsConstructor;

@Service("invoiceValidationService")
@AllArgsConstructor
public class InvoiceValidationService {
    private final InvoiceRepository invoiceRepository;

    public boolean existsById(@NonNull UUID id) {
        return invoiceRepository.existsById(id);
    }

    public boolean isOwnedByUserId(@NonNull UUID invoiceId, UUID userId) {
        return invoiceRepository.findById(invoiceId)
                .map(invoice -> invoice.getUser().getId().equals(userId))
                .orElse(false);
    }

    public boolean hasUserSufficientScore(@NonNull UUID invoiceId, int useScore) {
        return invoiceRepository.findById(invoiceId)
                .map(invoice -> invoice.getUser().getScore() >= useScore)
                .orElse(false);
    }
}
