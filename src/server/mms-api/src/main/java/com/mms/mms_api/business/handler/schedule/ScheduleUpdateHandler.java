package com.mms.mms_api.business.handler.schedule;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.schedule.ScheduleUpdateCommand;
import com.mms.mms_api.dto.ScheduleDto;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.util.mapper.ScheduleMapper;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Room;

public class ScheduleUpdateHandler extends ScheduleBaseHandler<ScheduleUpdateCommand, ScheduleDto> {
    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    public ScheduleUpdateHandler(ScheduleUpdateCommand request, ScheduleMapper scheduleMapper,
            ScheduleRepository scheduleRepository, MovieRepository movieRepository, RoomRepository roomRepository) {
        super(request, scheduleMapper, scheduleRepository);
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public ScheduleDto execute() {
        Schedule schedule = scheduleRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("schedule.notFound"));

        scheduleMapper.updateEntity(request, schedule);

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("movie.notFound"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("room.notFound"));

        schedule.setMovie(movie);
        schedule.setRoom(room);

        List<ScheduleSeat> scheduleSeats = schedule.getScheduleSeats();

        Map<UUID, Boolean> updatedStatuses = request.getSeatStatuses();

        if (updatedStatuses != null) {
            for (ScheduleSeat scheduleSeat : scheduleSeats) {
                Boolean newStatus = updatedStatuses.get(scheduleSeat.getId().getSeatId());
                if (newStatus != null) {
                    scheduleSeat.setReserved(newStatus);
                }
            }
        }

        Schedule updatedSchedule = scheduleRepository.save(schedule);

        return scheduleMapper.toDto(updatedSchedule);
    }
}
