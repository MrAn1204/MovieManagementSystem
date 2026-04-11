package com.mms.mms_api.business.handler.ticket;

import org.springframework.stereotype.Component;

import java.util.UUID;

import com.mms.mms_api.business.command.ticket.TicketDeleteCommand;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;

/**
 * Handles ticket delete commands.
 */
@Component
public class TicketDeleteHandler extends TicketBaseHandler<TicketDeleteCommand, Void> {

    /**
     * Creates a TicketDeleteHandler.
     *
     * @param ticketRepository ticket repository
     */
    public TicketDeleteHandler(TicketRepository ticketRepository) {
        super(null, ticketRepository);
    }

    /**
     * Deletes a ticket by its identifier.
     *
     * @param request delete command containing the target ticket id
     * @return {@code null}
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the ticket does not exist
     */
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