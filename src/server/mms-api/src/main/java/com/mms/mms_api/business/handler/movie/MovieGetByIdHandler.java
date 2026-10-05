package com.mms.mms_api.business.handler.movie;

import java.time.LocalDateTime;
import java.time.ZoneId;

import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.util.CurrentUserHelper;
import com.mms.mms_api.util.mapper.ScheduleMapper;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.movie.MovieGetByIdQuery;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.dto.AuditDto;
import com.mms.mms_api.dto.movie.MovieDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.util.mapper.MovieMapper;

/**
 * Handles requests to retrieve movie by id.
 */
@Component
public class MovieGetByIdHandler extends MovieBaseHandler<MovieGetByIdQuery, MovieDetailDto> {
    private final CurrentUserHelper currentUser;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    /**
     * Creates a MovieGetByIdHandler.
     *
     * @param movieMapper movie mapper
     * @param movieRepository movie repository
     * @param currentUserHelper current user helper
     */
    public MovieGetByIdHandler(MovieMapper movieMapper, MovieRepository movieRepository,
            CurrentUserHelper currentUserHelper, ScheduleRepository scheduleRepository,
            ScheduleMapper scheduleMapper) {
        super(movieMapper, movieRepository);
        this.currentUser = currentUserHelper;
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    /**
     * Retrieves a movie by its identifier.
     *
     * @param request query containing the target movie id
     * @return movie DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the movie does not exist
     */
    @Override
    public MovieDetailDto execute(MovieGetByIdQuery request) {
        Movie movie = movieRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("movie.notFound"));

        MovieDetailDto dto = movieMapper.toDetailDto(movie);

        dto.setSchedules(scheduleRepository
                .findByMovieIdAndShowTimeAfterOrderByShowTimeAsc(movie.getId(), LocalDateTime.now(ZoneId.systemDefault()))
                .stream()
                .map(scheduleMapper::toSummaryDto)
                .toList());

        if (currentUser.isAdmin()) {
            dto.setAudit(new AuditDto(movie));
        }

        return dto;
    }

}
