package com.mms.mms_api.business.handler.schedule;

import java.util.List;

import com.mms.mms_api.business.command.schedule.ScheduleUpdateCommand;
import com.mms.mms_api.dto.ScheduleDto;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.util.mapper.ScheduleMapper;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Room;

public class ScheduleUpdateHandler extends ScheduleBaseHandler<ScheduleUpdateCommand, ScheduleDto> {
    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    private final SeatRepository seatRepository;

    public ScheduleUpdateHandler(ScheduleUpdateCommand request, ScheduleMapper scheduleMapper,
            ScheduleRepository scheduleRepository, MovieRepository movieRepository, RoomRepository roomRepository,
            SeatRepository seatRepository) {
        super(request, scheduleMapper, scheduleRepository);
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public ScheduleDto execute() {
        Schedule schedule = scheduleRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.SCHEDULE_NOT_FOUND));

        scheduleMapper.updateEntity(request, schedule);

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.MOVIE_NOT_FOUND));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.ROOM_NOT_FOUND));

        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.SEAT_NOT_FOUND));

        schedule.setMovie(movie);
        schedule.setRoom(room);

        List<ScheduleSeat> scheduleSeats = schedule.getScheduleSeats();

        ScheduleSeatId ssId = new ScheduleSeatId(schedule.getId(), request.getSeatId());

        scheduleSeats.removeIf(ss -> ss.getId().equals(ssId));

        scheduleSeats.add(new ScheduleSeat(ssId, false, schedule, seat));

        Schedule updatedSchedule = scheduleRepository.save(schedule);

        return scheduleMapper.toDto(updatedSchedule);
    }
}
