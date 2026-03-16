package com.mms.mms_api.business.handler.room;

import java.util.Objects;

import com.mms.mms_api.business.command.room.RoomCreateCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.room.RoomDetailDto;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;

public class RoomCreateHandler extends RoomBaseHandler<RoomCreateCommand, RoomDetailDto> {
    public RoomCreateHandler(RoomCreateCommand request, RoomMapper roomMapper, RoomRepository roomRepository) {
        super(request, roomMapper, roomRepository);
    }

    @Override
    public RoomDetailDto execute() {
        Room room = roomMapper.toEntity(request);
        
        Room savedRoom = roomRepository.save(Objects.requireNonNull(room));

        return roomMapper.toDetailDto(savedRoom);
    }
    
}
