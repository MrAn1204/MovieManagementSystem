package com.mms.mms_api.business.handler.invoice;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.invoice.InvoiceGetByIdQuery;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.util.mapper.InvoiceMapper;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.dto.invoice.InvoiceDto;

import java.util.UUID;

@Component
public class InvoiceGetByIdHandler extends InvoiceBaseHandler<InvoiceGetByIdQuery, InvoiceDto> {

    public InvoiceGetByIdHandler(InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository) {
        super(invoiceMapper, invoiceRepository);
    }

    @Override
    public InvoiceDto execute(InvoiceGetByIdQuery request) {
        UUID invoiceId = request.getId();
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvalidInputException("invoice.notFound"));
        return invoiceMapper.toDto(invoice);
    }
}