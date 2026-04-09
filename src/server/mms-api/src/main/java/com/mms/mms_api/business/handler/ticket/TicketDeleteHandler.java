package com.mms.mms_api.business.handler.ticket;

import org.springframework.stereotype.Component;

import java.util.UUID;

import com.mms.mms_api.business.command.ticket.TicketDeleteCommand;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;

@Component
public class TicketDeleteHandler extends TicketBaseHandler<TicketDeleteCommand, Void> {

    public TicketDeleteHandler(TicketRepository ticketRepository) {
        super(null, ticketRepository);
    }

    @Override
    public Void execute(TicketDeleteCommand request) {
        UUID id = request.getId();

        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("ticket.notFound");
        }

        ticketRepository.deleteById(id);
        return null;
    }
}