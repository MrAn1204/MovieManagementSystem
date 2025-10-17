package com.mms.mms_api.business.handler.invoice;

import com.mms.mms_api.business.command.invoice.InvoiceUpdateCommand;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.InvoiceDto;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.InvoiceMapper;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public class InvoiceUpdateHandler extends InvoiceBaseHandler<InvoiceUpdateCommand, InvoiceDto> {
    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    public InvoiceUpdateHandler(InvoiceUpdateCommand request, InvoiceMapper invoiceMapper,
            InvoiceRepository invoiceRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        super(request, invoiceMapper, invoiceRepository);
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public InvoiceDto execute() {
        Invoice invoice = invoiceRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("invoice.notFound"));

        List<Ticket> tickets = ticketRepository.findByIdIn(request.getTicketIds());

        if (tickets.size() < request.getTicketIds().size()) {
            throw new InvalidInputException("invoice.tickets.invalid");
        }

        User user = userRepository.findById(invoice.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("user.notFound"));

        if (user.getScore() < request.getUseScore()) {
            throw new InvalidInputException("user.score.insufficient");
        }

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