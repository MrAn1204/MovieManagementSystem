package com.mms.mms_api.business.handler.ticket;

import com.mms.mms_api.business.command.ticket.TicketDeleteCommand;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Ticket;

public class TicketDeleteHandler extends TicketBaseHandler<TicketDeleteCommand, Void> {

    public TicketDeleteHandler(TicketDeleteCommand request, TicketRepository ticketRepository) {
        super(request, null, ticketRepository);
    }

    @Override
    public Void execute() {
        Ticket ticket = ticketRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ticket.notFound"));

        ticketRepository.delete(ticket);
        return null;
    }
}