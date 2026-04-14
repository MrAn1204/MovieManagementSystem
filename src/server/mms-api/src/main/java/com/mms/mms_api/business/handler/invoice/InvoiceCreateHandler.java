package com.mms.mms_api.business.handler.invoice;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.invoice.InvoiceCreateCommand;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.invoice.InvoiceDto;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.InvoiceMapper;

import java.util.List;

/**
 * Handles invoice creation commands.
 */
@Component
public class InvoiceCreateHandler extends InvoiceBaseHandler<InvoiceCreateCommand, InvoiceDto> {

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    /**
     * Creates an InvoiceCreateHandler.
     *
     * @param invoiceMapper invoice mapper
     * @param invoiceRepository invoice repository
     * @param ticketRepository ticket repository
     * @param userRepository user repository
     */
    public InvoiceCreateHandler(InvoiceMapper invoiceMapper,
            InvoiceRepository invoiceRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        super(invoiceMapper, invoiceRepository);
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new invoice, links the selected tickets and updates the user score.
     *
     * @param request invoice create command
     * @return created invoice DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the user does not exist
     */
    @Override
    public InvoiceDto execute(InvoiceCreateCommand request) {
        List<Ticket> tickets = ticketRepository.findByIdIn(request.getTicketIds());

        Invoice invoice = invoiceMapper.toEntity(request);

        for (Ticket ticket : tickets) {
            ticket.setInvoice(invoice);
        }

        invoice.setTickets(tickets);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user.notFound"));

        user.setScore(request.getUseScore(), request.getAddScore());

        invoice.setUser(user);
        invoice.setTotalMoney(tickets, request.getDiscount(), request.getUseScore());

        Invoice savedInvoice = invoiceRepository.save(invoice);

        return invoiceMapper.toDto(savedInvoice);
    }
}