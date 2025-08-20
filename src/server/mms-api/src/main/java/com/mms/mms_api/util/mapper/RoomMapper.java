package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.mms.mms_api.business.command.room.RoomCreateCommand;
import com.mms.mms_api.business.command.room.RoomUpdateCommand;
import com.mms.mms_api.dto.RoomDto;
import com.mms.mms_api.model.Room;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seats", ignore = true)
    Room toEntity(RoomCreateCommand command);

    @Mapping(target = "seats", ignore = true)
    RoomDto toDto(Room room);

    @Mapping(target = "seats", ignore = true)
    void updateEntity(RoomUpdateCommand command, @MappingTarget Room room);
}