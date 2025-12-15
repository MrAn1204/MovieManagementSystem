package com.mms.mms_api.business.handler.ticket;

import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.dto.TicketDto;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.Ticket;

public class TicketUpdateHandler extends TicketBaseHandler<TicketUpdateCommand, TicketDto> {
    private final ScheduleRepository scheduleRepository;

    private final SeatRepository seatRepository;

    private final InvoiceRepository invoiceRepository;

    private final PromotionRepository promotionRepository;

    private final ScheduleSeatRepository scheduleSeatRepository;

    public TicketUpdateHandler(TicketUpdateCommand request, TicketMapper ticketMapper,
            TicketRepository ticketRepository, ScheduleRepository scheduleRepository,
            SeatRepository seatRepository, InvoiceRepository invoiceRepository,
            PromotionRepository promotionRepository, ScheduleSeatRepository scheduleSeatRepository) {
        super(request, ticketMapper, ticketRepository);
        this.scheduleRepository = scheduleRepository;
        this.seatRepository = seatRepository;
        this.invoiceRepository = invoiceRepository;
        this.promotionRepository = promotionRepository;
        this.scheduleSeatRepository = scheduleSeatRepository;
    }

    @Override
    public TicketDto execute() {
        Ticket ticket = ticketRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ticket.notFound"));

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("schedule.notFound"));

        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("seat.notFound"));

        Promotion promotion = null;
        if (request.getPromotionId() != null) {
            promotion = promotionRepository.findById(request.getPromotionId())
                    .orElseThrow(() -> new ResourceNotFoundException("promotion.notFound"));
        }

        if (seat.getRoom().getId() != schedule.getRoom().getId()) {
            throw new InvalidInputException("ticket.room.mismatch");
        }

        ScheduleSeat scheduleSeat = scheduleSeatRepository.findById(new ScheduleSeatId(schedule.getId(), seat.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("schedule.seat.notFound"));

        ticketMapper.updateEntity(request, ticket);

        if (scheduleSeat.isReserved()) {
            throw new InvalidInputException("ticket.seat.reserved");
        } else {
            scheduleSeatRepository.findById(new ScheduleSeatId(ticket.getSchedule().getId(), ticket.getSeat().getId()))
                    .ifPresent(ss -> {
                        ss.setReserved(false);
                        scheduleSeatRepository.save(ss);
                    });

            scheduleSeat.setReserved(true);
            scheduleSeatRepository.save(scheduleSeat);
        }

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