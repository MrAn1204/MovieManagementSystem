package com.mms.mms_api.util.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.room.RoomCreateCommand;
import com.mms.mms_api.business.command.room.RoomUpdateCommand;
import com.mms.mms_api.dto.room.RoomDetailDto;
import com.mms.mms_api.dto.room.RoomDto;
import com.mms.mms_api.model.Room;

/**
 * Mapper for room commands and DTO projections.
 */
@Mapper(config = DefaultMapperConfig.class, uses = { SeatMapper.class })
public interface RoomMapper {
    @Mapping(target = "seats", ignore = true)
    Room toEntity(RoomCreateCommand command);

    @Mapping(target = "maxCapacity", expression = "java(room.getRowLength() * room.getColumnLength())")
    @Mapping(target = "currentCapacity", expression = "java(room.getSeats() != null ? room.getSeats().size() : 0)")
    RoomDto toDto(Room room);

    @InheritConfiguration(name = "toDto")
    @Mapping(target = "audit", ignore = true)
    RoomDetailDto toDetailDto(Room room);

    @Mapping(target = "seats", ignore = true)
    void updateEntity(RoomUpdateCommand command, @MappingTarget Room room);
}