package com.mms.mms_api.business.handler.invoice;

import com.mms.mms_api.business.query.invoice.InvoiceGetAllQuery;
import com.mms.mms_api.dto.InvoiceDto;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.util.mapper.InvoiceMapper;
import com.mms.mms_api.data.InvoiceRepository;
import java.util.List;

public class InvoiceGetAllHandler extends InvoiceBaseHandler<InvoiceGetAllQuery, List<InvoiceDto>> {

    public InvoiceGetAllHandler(InvoiceGetAllQuery request, InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository) {
        super(request, invoiceMapper, invoiceRepository);
    }

    @Override
    public List<InvoiceDto> execute() {
        List<Invoice> invoices = invoiceRepository.findAll();

        return invoices.stream()
                .map(invoiceMapper::toDto)
                .toList();
    }
}