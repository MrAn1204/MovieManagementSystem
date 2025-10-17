package com.mms.mms_api.business.handler.ticket;

import com.mms.mms_api.business.query.ticket.TicketGetAllQuery;
import com.mms.mms_api.dto.TicketDto;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Ticket;

import java.util.List;

public class TicketGetAllHandler extends TicketBaseHandler<TicketGetAllQuery, List<TicketDto>> {

    public TicketGetAllHandler(TicketGetAllQuery request, TicketMapper ticketMapper, TicketRepository ticketRepository) {
        super(request, ticketMapper, ticketRepository);
    }

    @Override
    public List<TicketDto> execute() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(ticketMapper::toDto).toList();
    }
}