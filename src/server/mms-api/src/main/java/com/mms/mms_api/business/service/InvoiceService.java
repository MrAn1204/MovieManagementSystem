package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.invoice.InvoiceCreateCommand;
import com.mms.mms_api.business.command.invoice.InvoiceDeleteCommand;
import com.mms.mms_api.business.command.invoice.InvoiceUpdateCommand;
import com.mms.mms_api.business.query.invoice.InvoiceGetAllQuery;
import com.mms.mms_api.business.query.invoice.InvoiceGetByIdQuery;
import com.mms.mms_api.dto.invoice.InvoiceDetailDto;
import com.mms.mms_api.dto.invoice.InvoiceDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.InvoiceValidator;

import lombok.AllArgsConstructor;

/**
 * Provides CRUD operations for invoices.
 */
@Service
@AllArgsConstructor
public class InvoiceService {
    private final RequestMediator mediator;
    private final InvoiceValidator invoiceValidator;

    /**
     * Creates an invoice.
     *
     * @param request create command
     * @return created invoice DTO
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public InvoiceDetailDto handle(InvoiceCreateCommand request) {
        invoiceValidator.validate(request);
        return mediator.execute(request);

    }

    /**
     * Returns all invoices.
     *
     * @param request get-all query
     * @return list of invoice DTOs
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<InvoiceDto> handle(InvoiceGetAllQuery request) {
        return mediator.execute(request);
    }

    /**
     * Returns invoice details by id.
     *
     * @param request get-by-id query
     * @return invoice DTO
     */
    @PreAuthorize("hasAuthority('ADMIN') || @invoiceValidationService.isOwnedByUserId(#request.id, authentication.principal.id)")
    public InvoiceDetailDto handle(InvoiceGetByIdQuery request) {
        return mediator.execute(request);
    }

    /**
     * Updates an invoice.
     *
     * @param request update command
     * @return updated invoice DTO
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public InvoiceDetailDto handle(InvoiceUpdateCommand request) {
        invoiceValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Deletes an invoice.
     *
     * @param request delete command
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void handle(InvoiceDeleteCommand request) {
        mediator.execute(request);
    }
}
