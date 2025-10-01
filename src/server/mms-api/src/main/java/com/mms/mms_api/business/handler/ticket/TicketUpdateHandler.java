package com.mms.mms_api.business.handler.ticket;

import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.dto.TicketDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.util.mapper.TicketMapper;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.Ticket;

public class TicketUpdateHandler extends BaseTicketHandler<TicketUpdateCommand, TicketDto> {
    private final ScheduleRepository scheduleRepository;

    private final SeatRepository seatRepository;

    public TicketUpdateHandler(TicketUpdateCommand request, TicketMapper ticketMapper,
            TicketRepository ticketRepository, ScheduleRepository scheduleRepository, SeatRepository seatRepository) {
        super(request, ticketMapper, ticketRepository);
        this.scheduleRepository = scheduleRepository;
        this.seatRepository = seatRepository;
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

        ticket.setSchedule(schedule);
        ticket.setSeat(seat);

        Ticket updatedTicket = ticketRepository.save(ticket);

        return ticketMapper.toDto(updatedTicket);
    }
}