package com.mms.mms_api.business.handler.invoice;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.invoice.InvoiceUpdateCommand;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.invoice.InvoiceDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.InvoiceMapper;

import java.util.List;

/**
 * Handles invoice update commands.
 */
@Component
public class InvoiceUpdateHandler extends InvoiceBaseHandler<InvoiceUpdateCommand, InvoiceDto> {
    private final TicketRepository ticketRepository;

    /**
     * Creates an InvoiceUpdateHandler.
     *
     * @param invoiceMapper invoice mapper
     * @param invoiceRepository invoice repository
     * @param ticketRepository ticket repository
     */
    public InvoiceUpdateHandler(InvoiceMapper invoiceMapper,
            InvoiceRepository invoiceRepository, TicketRepository ticketRepository) {
        super(invoiceMapper, invoiceRepository);
        this.ticketRepository = ticketRepository;
    }

    /**
     * Updates an existing invoice, re-links tickets and adjusts the user score accordingly.
     *
     * @param request invoice update command
     * @return updated invoice DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the invoice does not exist
     */
    @Override
    public InvoiceDto execute(InvoiceUpdateCommand request) {
        Invoice invoice = invoiceRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("invoice.notFound"));

        List<Ticket> tickets = ticketRepository.findByIdIn(request.getTicketIds());

        User user = invoice.getUser();

        invoiceMapper.updateEntity(request, invoice);

        for (Ticket oldTicket : invoice.getTickets()) {
            oldTicket.setInvoice(null);
        }

        for (Ticket newTicket : tickets) {
            newTicket.setInvoice(invoice);
        }

        invoice.setTickets(tickets);

        int useScoreDifference = request.getUseScore() - invoice.getUseScore();
        int addScoreDifference = request.getAddScore() - invoice.getAddScore();

        user.setScore(useScoreDifference, addScoreDifference);

        Invoice updatedInvoice = invoiceRepository.save(invoice);

        return invoiceMapper.toDto(updatedInvoice);
    }
}