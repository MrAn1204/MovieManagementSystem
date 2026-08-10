package com.mms.mms_api.util.mapper;

import java.time.format.DateTimeFormatter;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.schedule.ScheduleCreateCommand;
import com.mms.mms_api.business.command.schedule.ScheduleUpdateCommand;
import com.mms.mms_api.dto.schedule.ScheduleDetailDto;
import com.mms.mms_api.dto.schedule.ScheduleDto;
import com.mms.mms_api.dto.schedule.ScheduleSummaryDto;
import com.mms.mms_api.model.Schedule;

/**
 * Mapper for schedule commands and schedule DTO outputs.
 */
@Mapper(config = DefaultMapperConfig.class, uses = { MovieMapper.class, RoomMapper.class, SeatMapper.class })
public interface ScheduleMapper {
    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "scheduleSeats", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    Schedule toEntity(ScheduleCreateCommand command);

    @Mapping(target = "name", ignore = true)
    ScheduleDto toDto(Schedule schedule);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "seats", source = "schedule.scheduleSeats")
    @Mapping(target = "rowLength", source = "schedule.room.rowLength")
    @Mapping(target = "columnLength", source = "schedule.room.columnLength")
    @Mapping(target = "audit", ignore = true)
    ScheduleDetailDto toDetailDto(Schedule schedule);

    @Mapping(target = "roomName", source = "schedule.room.name")
    ScheduleSummaryDto toSummaryDto(Schedule schedule);

    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "scheduleSeats", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    void updateEntity(ScheduleUpdateCommand command, @MappingTarget Schedule schedule);

    @AfterMapping
    default void mapName(Schedule schedule, @MappingTarget ScheduleDto dto) {
        String movie = schedule.getMovie().getName();

        String room = schedule.getRoom().getName();

        String showTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(schedule.getShowTime());

        String name = movie + " - " + room + " - " + showTime;

        dto.setName(name);
    }

}