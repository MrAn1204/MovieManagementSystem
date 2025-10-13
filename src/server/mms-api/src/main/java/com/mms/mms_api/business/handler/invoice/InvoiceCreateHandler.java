package com.mms.mms_api.business.handler.invoice;

import com.mms.mms_api.business.command.invoice.InvoiceCreateCommand;
import com.mms.mms_api.dto.InvoiceDto;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.util.mapper.InvoiceMapper;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public class InvoiceCreateHandler extends InvoiceBaseHandler<InvoiceCreateCommand, InvoiceDto> {

    private final TicketRepository ticketRepository;

    public InvoiceCreateHandler(InvoiceCreateCommand request, InvoiceMapper invoiceMapper,
            InvoiceRepository invoiceRepository, TicketRepository ticketRepository) {
        super(request, invoiceMapper, invoiceRepository);
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public InvoiceDto execute() {
        List<Ticket> tickets = ticketRepository.findByIdIn(request.getTicketIds());

        if (tickets.size() < request.getTicketIds().size()) {
            throw new InvalidInputException("invoice.tickets.invalid");
        }

        Invoice invoice = invoiceMapper.toEntity(request);

        for (Ticket ticket : tickets) {
            ticket.setInvoice(invoice);
        }

        invoice.setTickets(tickets);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        return invoiceMapper.toDto(savedInvoice);
    }
}