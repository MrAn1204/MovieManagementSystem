package com.mms.mms_api.business.handler.invoice;

import com.mms.mms_api.business.query.invoice.InvoiceGetByIdQuery;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.util.mapper.InvoiceMapper;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.dto.invoice.InvoiceDto;

import java.util.UUID;

public class InvoiceGetByIdHandler extends InvoiceBaseHandler<InvoiceGetByIdQuery, InvoiceDto> {

    public InvoiceGetByIdHandler(InvoiceGetByIdQuery request, InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository) {
        super(request, invoiceMapper, invoiceRepository);
    }

    @Override
    public InvoiceDto execute() {
        UUID invoiceId = request.getId();
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvalidInputException("invoice.notFound"));
        return invoiceMapper.toDto(invoice);
    }
}