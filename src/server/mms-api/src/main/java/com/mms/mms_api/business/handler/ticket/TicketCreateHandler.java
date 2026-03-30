package com.mms.mms_api.business.handler.ticket;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.mms.mms_api.business.command.ticket.TicketCreateCommand;
import com.mms.mms_api.business.service.TicketDependencies;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.ticket.TicketDetailDto;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.model.User;

public class TicketCreateHandler extends TicketBaseHandler<TicketCreateCommand, List<TicketDetailDto>> {
    private final TicketDependencies ticketDependencies;

    private final ScheduleSeatRepository scheduleSeatRepository;

    public TicketCreateHandler(TicketCreateCommand request, TicketMapper ticketMapper,
            TicketRepository ticketRepository, TicketDependencies ticketDependencies,
            ScheduleSeatRepository scheduleSeatRepository) {
        super(request, ticketMapper, ticketRepository);
        this.ticketDependencies = ticketDependencies;
        this.scheduleSeatRepository = scheduleSeatRepository;
    }

    @Override
    public List<TicketDetailDto> execute() {
        Ticket ticket = ticketMapper.toEntity(request);

        Schedule schedule = ticketDependencies.getScheduleById(request.getScheduleId());

        List<Seat> seats = ticketDependencies.getSeatByIdIn(request.getSeatIds());

        UUID promotionId = request.getPromotionId();
        Promotion promotion = ticketDependencies.getPromotionById(promotionId);

        User user = ticketDependencies.getUserById(request.getUserId());

        ticket.setSchedule(schedule);
        ticket.setPromotion(promotion);
        ticket.setUser(user);

        List<ScheduleSeat> scheduleSeats = scheduleSeatRepository.findByScheduleAndSeatIn(schedule, seats);

        List<Ticket> newTickets = new LinkedList<>();

        for (ScheduleSeat scheduleSeat : scheduleSeats) {
            Seat seat = scheduleSeat.getSeat();

            ticket.setSeat(seat);
            ticket.setPrice(seat.getSeatType());

            scheduleSeat.setReserved(true);
            scheduleSeatRepository.save(scheduleSeat);
    
            newTickets.add(ticket);
        }

        List<Ticket> savedTickets = ticketRepository.saveAll(newTickets);

        return savedTickets.stream().map(ticketMapper::toDetailDto).toList();
    }
}