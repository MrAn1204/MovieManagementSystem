package com.mms.mms_api.business.handler.ticket;

import java.util.UUID;

import com.mms.mms_api.business.command.ticket.TicketCreateCommand;
import com.mms.mms_api.business.service.TicketDependencies;
import com.mms.mms_api.dto.TicketDto;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.model.User;

public class TicketCreateHandler extends TicketBaseHandler<TicketCreateCommand, TicketDto> {
    private final TicketDependencies ticketDependencies;

    private final ScheduleSeatRepository scheduleSeatRepository;

    public TicketCreateHandler(TicketCreateCommand request, TicketMapper ticketMapper,
            TicketRepository ticketRepository, TicketDependencies ticketDependencies, ScheduleSeatRepository scheduleSeatRepository) {
        super(request, ticketMapper, ticketRepository);
        this.ticketDependencies = ticketDependencies;
        this.scheduleSeatRepository = scheduleSeatRepository;
    }

    @Override
    public TicketDto execute() {
        Ticket ticket = ticketMapper.toEntity(request);

        Schedule schedule = ticketDependencies.getScheduleById(request.getScheduleId());

        Seat seat = ticketDependencies.getSeatById(request.getSeatId());

        UUID promotionId = request.getPromotionId();
        Promotion promotion = ticketDependencies.getPromotionById(promotionId);

        User user = ticketDependencies.getUserById(request.getUserId());

        if (seat.getRoom().getId() != schedule.getRoom().getId()) {
            throw new InvalidInputException("ticket.room.mismatch");
        }

        ticket.setSchedule(schedule);
        ticket.setSeat(seat);
        ticket.setPromotion(promotion);
        ticket.setUser(user);

        ScheduleSeat scheduleSeat = scheduleSeatRepository.findById(new ScheduleSeatId(schedule.getId(), seat.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("schedule.seat.notFound"));

        if (scheduleSeat.isReserved()) {
            throw new InvalidInputException("ticket.seat.reserved");
        } else {
            scheduleSeat.setReserved(true);
            scheduleSeatRepository.save(scheduleSeat);
        }

        Ticket savedTicket = ticketRepository.save(ticket);

        return ticketMapper.toDto(savedTicket);
    }
}