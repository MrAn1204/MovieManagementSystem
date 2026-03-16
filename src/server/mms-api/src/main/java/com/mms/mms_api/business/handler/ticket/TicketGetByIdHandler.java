package com.mms.mms_api.business.handler.ticket;

import com.mms.mms_api.business.query.ticket.TicketGetByIdQuery;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.ticket.TicketDetailDto;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Ticket;

public class TicketGetByIdHandler extends TicketBaseHandler<TicketGetByIdQuery, TicketDetailDto> {

    public TicketGetByIdHandler(TicketGetByIdQuery request, TicketMapper ticketMapper, TicketRepository ticketRepository) {
        super(request, ticketMapper, ticketRepository);
    }

    @Override
    public TicketDetailDto execute() {
        Ticket ticket = ticketRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ticket.notFound"));
        return ticketMapper.toDetailDto(ticket);
    }
}