package com.mms.mms_api.business.handler.schedule;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



import com.mms.mms_api.business.command.schedule.ScheduleCreateCommand;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.dto.schedule.ScheduleDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.util.mapper.ScheduleMapper;

/**
 * Handles schedule creation commands.
 */
@Component
public class ScheduleCreateHandler extends ScheduleBaseHandler<ScheduleCreateCommand, ScheduleDetailDto> {
    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    /**
     * Creates a ScheduleCreateHandler.
     *
     * @param scheduleMapper schedule mapper
     * @param scheduleRepository schedule repository
     * @param movieRepository movie repository
     * @param roomRepository room repository
     */
    public ScheduleCreateHandler(ScheduleMapper scheduleMapper,
            ScheduleRepository scheduleRepository, MovieRepository movieRepository, RoomRepository roomRepository) {
        super(scheduleMapper, scheduleRepository);
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Creates a new schedule, links the movie and room, and generates seat entries for all room seats.
     *
     * @param request schedule create command
     * @return created schedule detail DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the movie or room does not exist
     */
    @Override
    public ScheduleDetailDto execute(ScheduleCreateCommand request) {
        Schedule schedule = scheduleMapper.toEntity(request);

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("movie.notFound"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("room.notFound"));

        schedule.setMovie(movie);
        schedule.setRoom(room);

        Schedule newSchedule = scheduleRepository.save(schedule);

        List<Seat> seats = room.getSeats();

        List<ScheduleSeat> scheduleSeats = new ArrayList<>();

        for (Seat seat : seats) {
            ScheduleSeat scheduleSeat = new ScheduleSeat(
                    new ScheduleSeatId(schedule.getId(), seat.getId()),
                    schedule, seat);

            scheduleSeats.add(scheduleSeat);
        }

        newSchedule.setScheduleSeats(scheduleSeats);
        scheduleRepository.save(newSchedule);

        return scheduleMapper.toDetailDto(newSchedule);
    }
}
