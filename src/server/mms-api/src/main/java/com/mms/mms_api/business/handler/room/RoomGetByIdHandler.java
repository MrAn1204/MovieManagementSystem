package com.mms.mms_api.business.handler.room;

import com.mms.mms_api.business.query.room.RoomGetByIdQuery;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.RoomDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;

public class RoomGetByIdHandler extends RoomBaseHandler<RoomGetByIdQuery, RoomDetailDto> {
    public RoomGetByIdHandler(RoomGetByIdQuery request, RoomMapper roomMapper, RoomRepository roomRepository) {
        super(request, roomMapper, roomRepository);
    }

    @Override
    public RoomDetailDto execute() {
        Room room = roomRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("room.notFound"));

        return roomMapper.toDetailDto(room);
    }

}
