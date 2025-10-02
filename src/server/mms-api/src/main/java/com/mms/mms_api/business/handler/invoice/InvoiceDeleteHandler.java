package com.mms.mms_api.business.handler.invoice;

import com.mms.mms_api.business.command.invoice.InvoiceDeleteCommand;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Invoice;

public class InvoiceDeleteHandler extends InvoiceBaseHandler<InvoiceDeleteCommand, Void> {

    public InvoiceDeleteHandler(InvoiceDeleteCommand request, InvoiceRepository invoiceRepository) {
        super(request, null, invoiceRepository);
    }

    @Override
    public Void execute() {
        Invoice invoice = invoiceRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with ID: " + request.getId()));

        invoice.getTickets().forEach(ticket -> ticket.setInvoice(null));

        invoiceRepository.delete(invoice);

        return null;
    }
}