package com.mms.mms_api.business.handler.ticket;

import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.dto.TicketDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.Ticket;

public class TicketUpdateHandler extends BaseTicketHandler<TicketUpdateCommand, TicketDto> {
    private final ScheduleRepository scheduleRepository;

    private final SeatRepository seatRepository;

    private final InvoiceRepository invoiceRepository;

    private final PromotionRepository promotionRepository;

    public TicketUpdateHandler(TicketUpdateCommand request, TicketMapper ticketMapper,
            TicketRepository ticketRepository, ScheduleRepository scheduleRepository,
            SeatRepository seatRepository, InvoiceRepository invoiceRepository,
            PromotionRepository promotionRepository) {
        super(request, ticketMapper, ticketRepository);
        this.scheduleRepository = scheduleRepository;
        this.seatRepository = seatRepository;
        this.invoiceRepository = invoiceRepository;
        this.promotionRepository = promotionRepository;
    }

    @Override
    public TicketDto execute() {
        Ticket ticket = ticketRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ticket.notFound"));

        ticketMapper.updateEntity(request, ticket);

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("schedule.notFound"));

        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("seat.notFound"));

        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("invoice.notFound"));

        Promotion promotion = promotionRepository.findById(request.getPromotionId())
                .orElseThrow(() -> new ResourceNotFoundException("promotion.notFound"));

        ticket.setSchedule(schedule);
        ticket.setSeat(seat);
        ticket.setInvoice(invoice);
        ticket.setPromotion(promotion);

        Ticket updatedTicket = ticketRepository.save(ticket);

        return ticketMapper.toDto(updatedTicket);
    }
}