package com.mms.mms_api.business.handler.room;

import java.util.List;

import com.mms.mms_api.business.query.room.RoomGetAllQuery;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.RoomDto;
import com.mms.mms_api.dto.SeatDto;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;
import com.mms.mms_api.util.mapper.SeatMapper;

public class RoomGetAllHandler extends RoomBaseHandler<RoomGetAllQuery, List<RoomDto>> {
    private final SeatMapper seatMapper;

    public RoomGetAllHandler(RoomGetAllQuery request, RoomMapper roomMapper, SeatMapper seatMapper,
            RoomRepository roomRepository) {
        super(request, roomMapper, roomRepository);
        this.seatMapper = seatMapper;
    }

    @Override
    public List<RoomDto> execute() {
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream().map(room -> {
            List<SeatDto> seats = room.getSeats().stream().map(seatMapper::toDto).toList();

            RoomDto roomDto = roomMapper.toDto(room);
            roomDto.setSeats(seats);

            return roomDto;
        }).toList();
    }

}
