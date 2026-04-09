package com.mms.mms_api.business.handler.invoice;

import org.springframework.stereotype.Component;



import com.mms.mms_api.business.command.invoice.InvoiceDeleteCommand;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Invoice;

@Component
public class InvoiceDeleteHandler extends InvoiceBaseHandler<InvoiceDeleteCommand, Void> {

    public InvoiceDeleteHandler(InvoiceRepository invoiceRepository) {
        super(null, invoiceRepository);
    }

    @Override
    public Void execute(InvoiceDeleteCommand request) {
        Invoice invoice = invoiceRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("invoice.notFound"));

        invoice.getTickets().forEach(ticket -> ticket.setInvoice(null));

        invoiceRepository.delete(invoice);

        return null;
    }
}