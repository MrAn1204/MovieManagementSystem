package com.mms.mms_api.business.handler.invoice;

import org.springframework.stereotype.Component;



import com.mms.mms_api.business.command.invoice.InvoiceDeleteCommand;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Invoice;

/**
 * Handles invoice delete commands.
 */
@Component
public class InvoiceDeleteHandler extends InvoiceBaseHandler<InvoiceDeleteCommand, Void> {

    /**
     * Creates an InvoiceDeleteHandler.
     *
     * @param invoiceRepository invoice repository
     */
    public InvoiceDeleteHandler(InvoiceRepository invoiceRepository) {
        super(null, invoiceRepository);
    }

    /**
     * Deletes an invoice and detaches all associated tickets.
     *
     * @param request delete command containing the target invoice id
     * @return {@code null}
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the invoice does not exist
     */
    @Override
    public Void execute(InvoiceDeleteCommand request) {
        Invoice invoice = invoiceRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("invoice.notFound"));

        invoice.getTickets().forEach(ticket -> ticket.setInvoice(null));

        invoiceRepository.delete(invoice);

        return null;
    }
}