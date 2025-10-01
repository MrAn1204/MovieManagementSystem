package com.mms.mms_api.business.service;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.ticket.TicketCreateCommand;
import com.mms.mms_api.business.handler.ticket.TicketCreateHandler;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.TicketDto;
import com.mms.mms_api.util.mapper.TicketMapper;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    private final ScheduleRepository scheduleRepository;

    private final SeatRepository seatRepository;

    private final TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, ScheduleRepository scheduleRepository, SeatRepository seatRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.scheduleRepository = scheduleRepository;
        this.seatRepository = seatRepository;
        this.ticketMapper = ticketMapper;
    }

    public TicketDto handle(TicketCreateCommand request) {
        TicketCreateHandler handler = new TicketCreateHandler(request, ticketMapper, ticketRepository, scheduleRepository, seatRepository);
        return handler.execute();
    }
}
