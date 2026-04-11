package com.mms.mms_api.business.handler.invoice;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.invoice.InvoiceGetAllQuery;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.util.mapper.InvoiceMapper;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.dto.invoice.InvoiceDto;

import java.util.List;

/**
 * Handles requests to retrieve all invoices.
 */
@Component
public class InvoiceGetAllHandler extends InvoiceBaseHandler<InvoiceGetAllQuery, List<InvoiceDto>> {

    /**
     * Creates an InvoiceGetAllHandler.
     *
     * @param invoiceMapper invoice mapper
     * @param invoiceRepository invoice repository
     */
    public InvoiceGetAllHandler(InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository) {
        super(invoiceMapper, invoiceRepository);
    }

    /**
     * Retrieves all invoices.
     *
     * @param request query object
     * @return list of invoice DTOs
     */
    @Override
    public List<InvoiceDto> execute(InvoiceGetAllQuery request) {
        List<Invoice> invoices = invoiceRepository.findAll();

        return invoices.stream()
                .map(invoiceMapper::toDto)
                .toList();
    }
}