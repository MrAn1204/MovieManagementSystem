package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.schedule.ScheduleCreateCommand;
import com.mms.mms_api.business.command.schedule.ScheduleUpdateCommand;
import com.mms.mms_api.dto.ScheduleDto;
import com.mms.mms_api.model.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "scheduleSeats", ignore = true)
    Schedule toEntity(ScheduleCreateCommand dto);

    @Mapping(target = "movieName", source = "movie.name")
    @Mapping(target = "roomName", source = "room.name")
    ScheduleDto toDto(Schedule schedule);

    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "scheduleSeats", ignore = true)
    void updateEntity(ScheduleUpdateCommand command, @MappingTarget Schedule schedule);
}