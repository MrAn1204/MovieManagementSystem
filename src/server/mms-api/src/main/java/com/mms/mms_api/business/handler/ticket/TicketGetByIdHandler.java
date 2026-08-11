package com.mms.mms_api.business.handler.ticket;

import com.mms.mms_api.util.CurrentUserHelper;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.ticket.TicketGetByIdQuery;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.AuditDto;
import com.mms.mms_api.dto.ticket.TicketDetailDto;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Ticket;

/**
 * Handles requests to retrieve ticket by id.
 */
@Component
public class TicketGetByIdHandler extends TicketBaseHandler<TicketGetByIdQuery, TicketDetailDto> {

    private final CurrentUserHelper currentUser;

    /**
     * Creates a TicketGetByIdHandler.
     *
     * @param ticketMapper ticket mapper
     * @param ticketRepository ticket repository
     * @param currentUserHelper current user helper
     */
    public TicketGetByIdHandler(TicketMapper ticketMapper, TicketRepository ticketRepository, CurrentUserHelper currentUserHelper) {
        super(ticketMapper, ticketRepository);
        this.currentUser = currentUserHelper;
    }

    /**
     * Retrieves a ticket by its identifier.
     *
     * @param request query containing the target ticket id
     * @return ticket detail DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the ticket does not exist
     */
    @Override
    public TicketDetailDto execute(TicketGetByIdQuery request) {
        Ticket ticket = ticketRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ticket.notFound"));
        TicketDetailDto dto = ticketMapper.toDetailDto(ticket);

        if (currentUser.isAdmin()) {
            dto.setAudit(new AuditDto(ticket));
        }

        return dto;
    }
}