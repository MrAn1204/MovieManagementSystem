package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.mms.mms_api.business.command.seat.SeatCreateCommand;
import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.dto.SeatDto;
import com.mms.mms_api.model.Seat;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SeatMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "scheduleSeats", ignore = true)
    Seat toEntity(SeatCreateCommand command);

    @Mapping(target = "roomName", source = "room.name")
    SeatDto toDto(Seat seat);

    @Mapping(target = "room", ignore = true)
    @Mapping(target = "scheduleSeats", ignore = true)
    void updateEntity(SeatUpdateCommand command, @MappingTarget Seat seat);
}
