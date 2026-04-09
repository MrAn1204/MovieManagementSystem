package com.mms.mms_api.business.handler.room;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.room.RoomGetByIdQuery;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.room.RoomDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;

@Component
public class RoomGetByIdHandler extends RoomBaseHandler<RoomGetByIdQuery, RoomDetailDto> {
    public RoomGetByIdHandler(RoomMapper roomMapper, RoomRepository roomRepository) {
        super(roomMapper, roomRepository);
    }

    @Override
    public RoomDetailDto execute(RoomGetByIdQuery request) {
        Room room = roomRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("room.notFound"));

        return roomMapper.toDetailDto(room);
    }

}
