package com.mms.mms_api.business.handler.ticket;

import org.springframework.stereotype.Component;

import java.util.UUID;

import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.business.service.TicketDependencies;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.ticket.TicketDetailDto;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.Ticket;

@Component
public class TicketUpdateHandler extends TicketBaseHandler<TicketUpdateCommand, TicketDetailDto> {
    private final TicketDependencies ticketDependencies;

    private final InvoiceRepository invoiceRepository;

    private final ScheduleSeatRepository scheduleSeatRepository;

    public TicketUpdateHandler(TicketMapper ticketMapper,
            TicketRepository ticketRepository, TicketDependencies ticketDependencies,
            InvoiceRepository invoiceRepository, ScheduleSeatRepository scheduleSeatRepository) {
        super(ticketMapper, ticketRepository);
        this.ticketDependencies = ticketDependencies;
        this.invoiceRepository = invoiceRepository;
        this.scheduleSeatRepository = scheduleSeatRepository;
    }

    @Override
    public TicketDetailDto execute(TicketUpdateCommand request) {
        Ticket ticket = ticketRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ticket.notFound"));

        Schedule schedule = ticketDependencies.getScheduleById(request.getScheduleId());

        Seat seat = ticketDependencies.getSeatById(request.getSeatId());

        UUID promotionId = request.getPromotionId();
        Promotion promotion = ticketDependencies.getPromotionById(promotionId);

        ScheduleSeat scheduleSeat = scheduleSeatRepository.findById(new ScheduleSeatId(schedule.getId(), seat.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("schedule.seat.notFound"));

        ticketMapper.updateEntity(request, ticket);

        scheduleSeatRepository.findById(new ScheduleSeatId(ticket.getSchedule().getId(), ticket.getSeat().getId()))
                .ifPresent(ss -> {
                    ss.setReserved(false);
                    scheduleSeatRepository.save(ss);
                });

        scheduleSeat.setReserved(true);
        scheduleSeatRepository.save(scheduleSeat);

        ticket.setSchedule(schedule);
        ticket.setSeat(seat);
        ticket.setPromotion(promotion);

        ticket.setPrice(seat.getSeatType());

        Ticket updatedTicket = ticketRepository.save(ticket);

        Invoice invoice = updatedTicket.getInvoice();
        if (invoice != null) {
            invoice.setTotalMoney(invoice.getTickets(), invoice.getDiscount(), invoice.getUseScore());
            invoiceRepository.save(invoice);
        }

        return ticketMapper.toDetailDto(updatedTicket);
    }
}