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
import com.mms.mms_api.dto.invoice.InvoiceDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.InvoiceValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceService {
    private final RequestMediator mediator;
    private final InvoiceValidator invoiceValidator;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public InvoiceDto handle(InvoiceCreateCommand request) {
        invoiceValidator.validate(request);
        return mediator.execute(request);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<InvoiceDto> handle(InvoiceGetAllQuery request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN') || @invoiceValidationService.isOwnedByUserId(#request.id, authentication.principal.id)")
    public InvoiceDto handle(InvoiceGetByIdQuery request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public InvoiceDto handle(InvoiceUpdateCommand request) {
        invoiceValidator.validate(request);
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void handle(InvoiceDeleteCommand request) {
        mediator.execute(request);
    }
}
