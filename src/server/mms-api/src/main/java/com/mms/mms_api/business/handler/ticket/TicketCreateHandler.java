package com.mms.mms_api.business.handler.ticket;

import java.util.UUID;

import com.mms.mms_api.business.command.ticket.TicketCreateCommand;
import com.mms.mms_api.dto.TicketDto;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.model.User;

public class TicketCreateHandler extends TicketBaseHandler<TicketCreateCommand, TicketDto> {
    private final ScheduleRepository scheduleRepository;

    private final SeatRepository seatRepository;

    private final PromotionRepository promotionRepository;

    private final UserRepository userRepository;

    private final ScheduleSeatRepository scheduleSeatRepository;

    public TicketCreateHandler(TicketCreateCommand request, TicketMapper ticketMapper,
            TicketRepository ticketRepository, ScheduleRepository scheduleRepository, SeatRepository seatRepository,
            PromotionRepository promotionRepository, UserRepository userRepository,
            ScheduleSeatRepository scheduleSeatRepository) {
        super(request, ticketMapper, ticketRepository);
        this.scheduleRepository = scheduleRepository;
        this.seatRepository = seatRepository;
        this.promotionRepository = promotionRepository;
        this.userRepository = userRepository;
        this.scheduleSeatRepository = scheduleSeatRepository;
    }

    @Override
    public TicketDto execute() {
        Ticket ticket = ticketMapper.toEntity(request);

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("schedule.notFound"));

        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException("seat.notFound"));

        UUID promotionId = request.getPromotionId();
        Promotion promotion = (promotionId != null)
                ? promotionRepository.findById(promotionId)
                        .orElseThrow(() -> new ResourceNotFoundException("promotion.notFound"))
                : null;

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user.notFound"));

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