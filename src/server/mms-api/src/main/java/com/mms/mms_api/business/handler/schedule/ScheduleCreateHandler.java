package com.mms.mms_api.business.handler.schedule;

import java.util.ArrayList;
import java.util.List;

import com.mms.mms_api.business.command.schedule.ScheduleCreateCommand;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.ScheduleDto;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.util.mapper.ScheduleMapper;

public class ScheduleCreateHandler extends ScheduleBaseHandler<ScheduleCreateCommand, ScheduleDto> {
    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    private final SeatRepository seatRepository;

    public ScheduleCreateHandler(ScheduleCreateCommand request, ScheduleMapper scheduleMapper,
            ScheduleRepository scheduleRepository, MovieRepository movieRepository, RoomRepository roomRepository,
            SeatRepository seatRepository) {
        super(request, scheduleMapper, scheduleRepository);
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public ScheduleDto execute() {
        Schedule schedule = scheduleMapper.toEntity(request);

        movieRepository.findById(request.getMovieId()).ifPresent(schedule::setMovie);

        roomRepository.findById(request.getRoomId()).ifPresent(schedule::setRoom);

        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.SEAT_NOT_FOUND));

        Schedule newSchedule = scheduleRepository.save(schedule);

        List<ScheduleSeat> scheduleSeats = new ArrayList<>();
        ScheduleSeat scheduleSeat = new ScheduleSeat(
                new ScheduleSeatId(newSchedule.getId(), seat.getId()),
                false, newSchedule, seat);
        scheduleSeats.add(scheduleSeat);

        newSchedule.setScheduleSeats(scheduleSeats);
        scheduleRepository.save(newSchedule);

        return scheduleMapper.toDto(newSchedule);
    }
}
