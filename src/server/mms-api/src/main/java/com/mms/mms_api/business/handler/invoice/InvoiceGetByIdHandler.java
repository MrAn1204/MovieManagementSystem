package com.mms.mms_api.business.handler.invoice;

import com.mms.mms_api.business.query.invoice.InvoiceGetByIdQuery;
import com.mms.mms_api.dto.InvoiceDto;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.util.mapper.InvoiceMapper;
import com.mms.mms_api.data.InvoiceRepository;
import java.util.UUID;

public class InvoiceGetByIdHandler extends InvoiceBaseHandler<InvoiceGetByIdQuery, InvoiceDto> {

    public InvoiceGetByIdHandler(InvoiceGetByIdQuery request, InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository) {
        super(request, invoiceMapper, invoiceRepository);
    }

    @Override
    public InvoiceDto execute() {
        UUID invoiceId = request.getId();
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new IllegalArgumentException("invoice.notFound"));
        return invoiceMapper.toDto(invoice);
    }
}