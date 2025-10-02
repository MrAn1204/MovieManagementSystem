package com.mms.mms_api.business.handler.invoice;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.util.mapper.InvoiceMapper;

public abstract class InvoiceBaseHandler<I, O> extends BaseHandler<I, O> {
    protected InvoiceMapper invoiceMapper;

    protected InvoiceRepository invoiceRepository;
    
    protected InvoiceBaseHandler(I request, InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository) {
        super(request);
        this.invoiceMapper = invoiceMapper;
        this.invoiceRepository = invoiceRepository;
    }
}