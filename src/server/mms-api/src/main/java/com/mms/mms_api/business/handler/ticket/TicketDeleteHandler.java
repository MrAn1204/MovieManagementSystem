package com.mms.mms_api.business.handler.ticket;

import java.util.UUID;

import com.mms.mms_api.business.command.ticket.TicketDeleteCommand;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;

public class TicketDeleteHandler extends TicketBaseHandler<TicketDeleteCommand, Void> {

    public TicketDeleteHandler(TicketDeleteCommand request, TicketRepository ticketRepository) {
        super(request, null, ticketRepository);
    }

    @Override
    public Void execute() {
        UUID id = request.getId();

        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("ticket.notFound");
        }

        ticketRepository.deleteById(id);
        return null;
    }
}