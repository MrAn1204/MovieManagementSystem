package com.mms.mms_api.business.handler.ticket;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.ticket.TicketGetAllQuery;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.ticket.TicketDto;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Ticket;

import java.util.List;

@Component
public class TicketGetAllHandler extends TicketBaseHandler<TicketGetAllQuery, List<TicketDto>> {

    public TicketGetAllHandler(TicketMapper ticketMapper, TicketRepository ticketRepository) {
        super(ticketMapper, ticketRepository);
    }

    @Override
    public List<TicketDto> execute(TicketGetAllQuery request) {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(ticketMapper::toDto).toList();
    }
}