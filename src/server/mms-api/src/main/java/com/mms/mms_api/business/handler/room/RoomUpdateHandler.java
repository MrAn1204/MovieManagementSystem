package com.mms.mms_api.business.handler.room;

import org.springframework.stereotype.Component;

import java.util.Objects;

import com.mms.mms_api.business.command.room.RoomUpdateCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.room.RoomDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;

@Component
public class RoomUpdateHandler extends RoomBaseHandler<RoomUpdateCommand, RoomDetailDto> {
    public RoomUpdateHandler(RoomMapper roomMapper, RoomRepository roomRepository) {
        super(roomMapper, roomRepository);
    }

    @Override
    public RoomDetailDto execute(RoomUpdateCommand request) {
        Room room = roomRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("room.notFound"));

        roomMapper.updateEntity(request, room);

        Room updatedRoom = roomRepository.save(Objects.requireNonNull(room));

        return roomMapper.toDetailDto(updatedRoom);
    }

}
