package com.mms.mms_api.business.handler.invoice;

import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.invoice.InvoiceDeleteCommand;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Invoice;

public class InvoiceDeleteHandler extends InvoiceBaseHandler<InvoiceDeleteCommand, Void> {

    public InvoiceDeleteHandler(InvoiceDeleteCommand request, InvoiceRepository invoiceRepository) {
        super(request, null, invoiceRepository);
    }

    @Override
    @Transactional
    public Void execute() {
        Invoice invoice = invoiceRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("invoice.notFound"));

        invoice.getTickets().forEach(ticket -> ticket.setInvoice(null));

        invoiceRepository.delete(invoice);

        return null;
    }
}