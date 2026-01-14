package com.mms.mms_api.business.handler.room;

import java.util.Objects;

import com.mms.mms_api.business.command.room.RoomUpdateCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.RoomDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;

public class RoomUpdateHandler extends RoomBaseHandler<RoomUpdateCommand, RoomDto> {
    public RoomUpdateHandler(RoomUpdateCommand request, RoomMapper roomMapper, RoomRepository roomRepository) {
        super(request, roomMapper, roomRepository);
    }

    @Override
    public RoomDto execute() {
        Room room = roomRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("room.notFound"));

        roomMapper.updateEntity(request, room);

        Room updatedRoom = roomRepository.save(Objects.requireNonNull(room));

        return roomMapper.toDto(updatedRoom);
    }

}
