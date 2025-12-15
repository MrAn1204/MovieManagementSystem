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
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.TicketDto;
import com.mms.mms_api.util.mapper.TicketMapper;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    private final ScheduleRepository scheduleRepository;

    private final SeatRepository seatRepository;

    private final InvoiceRepository invoiceRepository;

    private final PromotionRepository promotionRepository;

    private final UserRepository userRepository;

    private final ScheduleSeatRepository scheduleSeatRepository;

    private final TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, ScheduleRepository scheduleRepository,
            SeatRepository seatRepository, InvoiceRepository invoiceRepository,
            PromotionRepository promotionRepository, UserRepository userRepository,
            ScheduleSeatRepository scheduleSeatRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.scheduleRepository = scheduleRepository;
        this.seatRepository = seatRepository;
        this.invoiceRepository = invoiceRepository;
        this.promotionRepository = promotionRepository;
        this.userRepository = userRepository;
        this.ticketMapper = ticketMapper;
        this.scheduleSeatRepository = scheduleSeatRepository;
    }

    @Transactional
    public TicketDto handle(TicketCreateCommand request) {
        TicketCreateHandler handler = new TicketCreateHandler(request, ticketMapper, ticketRepository,
                scheduleRepository, seatRepository, promotionRepository, userRepository, scheduleSeatRepository);
        return handler.execute();
    }

    public List<TicketDto> handle(TicketGetAllQuery request) {
        TicketGetAllHandler handler = new TicketGetAllHandler(request, ticketMapper, ticketRepository);
        return handler.execute();
    }

    public TicketDto handle(TicketGetByIdQuery request) {
        TicketGetByIdHandler handler = new TicketGetByIdHandler(request, ticketMapper, ticketRepository);
        return handler.execute();
    }

    @Transactional
    public TicketDto handle(TicketUpdateCommand request) {
        TicketUpdateHandler handler = new TicketUpdateHandler(request, ticketMapper, ticketRepository,
                scheduleRepository, seatRepository, invoiceRepository, promotionRepository, scheduleSeatRepository);
        return handler.execute();
    }

    @Transactional
    public void handle(TicketDeleteCommand request) {
        TicketDeleteHandler handler = new TicketDeleteHandler(request, ticketRepository);
        handler.execute();
    }

    public PaginatedResult<TicketDto> handle(TicketSearchQuery request) {
        TicketSearchHandler handler = new TicketSearchHandler(request, ticketMapper, ticketRepository);
        return handler.execute();
    }
}
