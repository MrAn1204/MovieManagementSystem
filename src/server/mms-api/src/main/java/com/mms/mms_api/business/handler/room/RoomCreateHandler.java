package com.mms.mms_api.business.handler.room;

import java.util.Objects;

import com.mms.mms_api.business.command.room.RoomCreateCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.room.RoomDto;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;

public class RoomCreateHandler extends RoomBaseHandler<RoomCreateCommand, RoomDto> {
    public RoomCreateHandler(RoomCreateCommand request, RoomMapper roomMapper, RoomRepository roomRepository) {
        super(request, roomMapper, roomRepository);
    }

    @Override
    public RoomDto execute() {
        Room room = roomMapper.toEntity(request);
        
        Room savedRoom = roomRepository.save(Objects.requireNonNull(room));

        return roomMapper.toDto(savedRoom);
    }
    
}
