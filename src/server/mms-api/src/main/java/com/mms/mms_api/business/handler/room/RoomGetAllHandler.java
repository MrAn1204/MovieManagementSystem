package com.mms.mms_api.business.handler.room;

import org.springframework.stereotype.Component;

import java.util.List;

import com.mms.mms_api.business.query.room.RoomGetAllQuery;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.room.RoomDto;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;

@Component
public class RoomGetAllHandler extends RoomBaseHandler<RoomGetAllQuery, List<RoomDto>> {
    public RoomGetAllHandler(RoomMapper roomMapper, RoomRepository roomRepository) {
        super(roomMapper, roomRepository);
    }

    @Override
    public List<RoomDto> execute(RoomGetAllQuery request) {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream().map(room -> roomMapper.toDto(room)).toList();
    }

}
