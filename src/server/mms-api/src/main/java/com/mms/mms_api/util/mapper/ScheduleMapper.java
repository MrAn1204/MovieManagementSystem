package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.schedule.ScheduleCreateCommand;
import com.mms.mms_api.business.command.schedule.ScheduleUpdateCommand;
import com.mms.mms_api.dto.ScheduleDto;
import com.mms.mms_api.model.Schedule;

@Mapper(config = DefaultMapperConfig.class, uses = { MovieMapper.class, RoomMapper.class })
public interface ScheduleMapper {
    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "scheduleSeats", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    Schedule toEntity(ScheduleCreateCommand command);

    ScheduleDto toDto(Schedule schedule);

    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "scheduleSeats", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    void updateEntity(ScheduleUpdateCommand command, @MappingTarget Schedule schedule);
}