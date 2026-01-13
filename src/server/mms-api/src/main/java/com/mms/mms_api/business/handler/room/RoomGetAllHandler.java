package com.mms.mms_api.business.handler.room;

import java.util.List;

import com.mms.mms_api.business.query.room.RoomGetAllQuery;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.RoomDto;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;

public class RoomGetAllHandler extends RoomBaseHandler<RoomGetAllQuery, List<RoomDto>> {
    public RoomGetAllHandler(RoomGetAllQuery request, RoomMapper roomMapper, RoomRepository roomRepository) {
        super(request, roomMapper, roomRepository);
    }

    @Override
    public List<RoomDto> execute() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream().map(room -> roomMapper.toDto(room)).toList();
    }

}
