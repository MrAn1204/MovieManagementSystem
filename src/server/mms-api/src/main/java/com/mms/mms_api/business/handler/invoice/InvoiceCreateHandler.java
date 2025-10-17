package com.mms.mms_api.business.handler.invoice;

import com.mms.mms_api.business.command.invoice.InvoiceCreateCommand;
import com.mms.mms_api.dto.InvoiceDto;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.InvoiceMapper;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public class InvoiceCreateHandler extends InvoiceBaseHandler<InvoiceCreateCommand, InvoiceDto> {

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    public InvoiceCreateHandler(InvoiceCreateCommand request, InvoiceMapper invoiceMapper,
            InvoiceRepository invoiceRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        super(request, invoiceMapper, invoiceRepository);
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
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

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user.notFound"));

        user.setScore(request.getUseScore(), request.getAddScore());

        invoice.setUser(user);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        return invoiceMapper.toDto(savedInvoice);
    }
}