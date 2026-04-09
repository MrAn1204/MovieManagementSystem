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

@Component
public class InvoiceUpdateHandler extends InvoiceBaseHandler<InvoiceUpdateCommand, InvoiceDto> {
    private final TicketRepository ticketRepository;

    public InvoiceUpdateHandler(InvoiceMapper invoiceMapper,
            InvoiceRepository invoiceRepository, TicketRepository ticketRepository) {
        super(invoiceMapper, invoiceRepository);
        this.ticketRepository = ticketRepository;
    }

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