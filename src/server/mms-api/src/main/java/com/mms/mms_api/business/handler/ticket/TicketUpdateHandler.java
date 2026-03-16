package com.mms.mms_api.business.handler.ticket;

import java.util.UUID;

import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.business.service.TicketDependencies;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.ticket.TicketDto;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.Ticket;

public class TicketUpdateHandler extends TicketBaseHandler<TicketUpdateCommand, TicketDto> {
    private final TicketDependencies ticketDependencies;

    private final InvoiceRepository invoiceRepository;

    private final ScheduleSeatRepository scheduleSeatRepository;

    public TicketUpdateHandler(TicketUpdateCommand request, TicketMapper ticketMapper,
            TicketRepository ticketRepository, TicketDependencies ticketDependencies,
            InvoiceRepository invoiceRepository, ScheduleSeatRepository scheduleSeatRepository) {
        super(request, ticketMapper, ticketRepository);
        this.ticketDependencies = ticketDependencies;
        this.invoiceRepository = invoiceRepository;
        this.scheduleSeatRepository = scheduleSeatRepository;
    }

    @Override
    public TicketDto execute() {
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

        Ticket updatedTicket = ticketRepository.save(ticket);

        Invoice invoice = updatedTicket.getInvoice();
        if (invoice != null) {
            int totalTicketPrice = invoice.getTickets().stream().mapToInt(Ticket::getPrice).sum();
            int totalMoney = (int) Math.round(totalTicketPrice * (1 - invoice.getDiscount()));

            invoice.setTotalMoney(totalMoney);
            invoiceRepository.save(invoice);
        }

        return ticketMapper.toDto(updatedTicket);
    }
}