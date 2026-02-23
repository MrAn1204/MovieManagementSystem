package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.ticket.TicketCreateCommand;
import com.mms.mms_api.business.command.ticket.TicketDeleteCommand;
import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.business.handler.ticket.TicketCreateHandler;
import com.mms.mms_api.business.handler.ticket.TicketDeleteHandler;
import com.mms.mms_api.business.handler.ticket.TicketGetAllHandler;
import com.mms.mms_api.business.handler.ticket.TicketGetByIdHandler;
import com.mms.mms_api.business.handler.ticket.TicketSearchHandler;
import com.mms.mms_api.business.handler.ticket.TicketUpdateHandler;
import com.mms.mms_api.business.query.ticket.TicketGetAllQuery;
import com.mms.mms_api.business.query.ticket.TicketGetByIdQuery;
import com.mms.mms_api.business.query.ticket.TicketSearchQuery;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.TicketDetailDto;
import com.mms.mms_api.dto.TicketDto;
import com.mms.mms_api.util.mapper.TicketMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    private final TicketDependencies ticketDependencies;

    private final ScheduleSeatRepository scheduleSeatRepository;

    private final InvoiceRepository invoiceRepository;

    private final TicketMapper ticketMapper;

    @Transactional
    public TicketDto handle(TicketCreateCommand request) {
        TicketCreateHandler handler = new TicketCreateHandler(request, ticketMapper, ticketRepository, ticketDependencies, scheduleSeatRepository);
        return handler.execute();
    }

    public List<TicketDetailDto> handle(TicketGetAllQuery request) {
        TicketGetAllHandler handler = new TicketGetAllHandler(request, ticketMapper, ticketRepository);
        return handler.execute();
    }

    public TicketDetailDto handle(TicketGetByIdQuery request) {
        TicketGetByIdHandler handler = new TicketGetByIdHandler(request, ticketMapper, ticketRepository);
        return handler.execute();
    }

    @Transactional
    public TicketDto handle(TicketUpdateCommand request) {
        TicketUpdateHandler handler = new TicketUpdateHandler(request, ticketMapper, ticketRepository, ticketDependencies, invoiceRepository, scheduleSeatRepository);
        return handler.execute();
    }

    @Transactional
    public void handle(TicketDeleteCommand request) {
        TicketDeleteHandler handler = new TicketDeleteHandler(request, ticketRepository);
        handler.execute();
    }

    public PaginatedResult<TicketDetailDto> handle(TicketSearchQuery request) {
        TicketSearchHandler handler = new TicketSearchHandler(request, ticketMapper, ticketRepository);
        return handler.execute();
    }
}
