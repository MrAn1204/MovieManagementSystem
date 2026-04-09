package com.mms.mms_api.business.handler.ticket;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.ticket.TicketGetByIdQuery;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.ticket.TicketDetailDto;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Ticket;

@Component
public class TicketGetByIdHandler extends TicketBaseHandler<TicketGetByIdQuery, TicketDetailDto> {

    public TicketGetByIdHandler(TicketMapper ticketMapper, TicketRepository ticketRepository) {
        super(ticketMapper, ticketRepository);
    }

    @Override
    public TicketDetailDto execute(TicketGetByIdQuery request) {
        Ticket ticket = ticketRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ticket.notFound"));
        return ticketMapper.toDetailDto(ticket);
    }
}