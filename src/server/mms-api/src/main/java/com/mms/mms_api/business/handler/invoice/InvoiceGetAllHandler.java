package com.mms.mms_api.business.handler.invoice;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.invoice.InvoiceGetAllQuery;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.util.mapper.InvoiceMapper;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.dto.invoice.InvoiceDto;

import java.util.List;

@Component
public class InvoiceGetAllHandler extends InvoiceBaseHandler<InvoiceGetAllQuery, List<InvoiceDto>> {

    public InvoiceGetAllHandler(InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository) {
        super(invoiceMapper, invoiceRepository);
    }

    @Override
    public List<InvoiceDto> execute(InvoiceGetAllQuery request) {
        List<Invoice> invoices = invoiceRepository.findAll();

        return invoices.stream()
                .map(invoiceMapper::toDto)
                .toList();
    }
}