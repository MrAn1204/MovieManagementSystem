package com.mms.mms_api.business.handler.invoice;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.util.mapper.InvoiceMapper;

/**
 * Base handler for invoice-related requests.
 */
public abstract class InvoiceBaseHandler<I, O> extends BaseHandler<I, O> {
    protected InvoiceMapper invoiceMapper;

    protected InvoiceRepository invoiceRepository;
    
    /**
     * Creates an invoice base handler.
     *
     * @param invoiceMapper invoice mapper
     * @param invoiceRepository invoice repository
     */
    protected InvoiceBaseHandler(InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository) {
        this.invoiceMapper = invoiceMapper;
        this.invoiceRepository = invoiceRepository;
    }
}