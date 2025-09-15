package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.room.RoomCreateCommand;
import com.mms.mms_api.business.command.room.RoomDeleteCommand;
import com.mms.mms_api.business.command.room.RoomUpdateCommand;
import com.mms.mms_api.business.handler.room.RoomCreateHandler;
import com.mms.mms_api.business.handler.room.RoomDeleteHandler;
import com.mms.mms_api.business.handler.room.RoomGetAllHandler;
import com.mms.mms_api.business.handler.room.RoomGetByIdHandler;
import com.mms.mms_api.business.handler.room.RoomUpdateHandler;
import com.mms.mms_api.business.query.room.RoomGetAllQuery;
import com.mms.mms_api.business.query.room.RoomGetByIdQuery;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.RoomDto;
import com.mms.mms_api.util.mapper.RoomMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    private final RoomMapper roomMapper;

    public RoomDto handle(RoomCreateCommand request) {
        RoomCreateHandler handler = new RoomCreateHandler(request, roomMapper, roomRepository);
        return handler.execute();
    }

    public List<RoomDto> handle(RoomGetAllQuery request) {
        RoomGetAllHandler handler = new RoomGetAllHandler(request, roomMapper, roomRepository);
        return handler.execute();
    }

    public RoomDto handle(RoomGetByIdQuery request) {
        RoomGetByIdHandler handler = new RoomGetByIdHandler(request, roomMapper, roomRepository);
        return handler.execute();
    }

    public RoomDto handle(RoomUpdateCommand request) {
        RoomUpdateHandler handler = new RoomUpdateHandler(request, roomMapper, roomRepository);
        return handler.execute();
    }

    public void handle(RoomDeleteCommand request) {
        RoomDeleteHandler handler = new RoomDeleteHandler(request, roomMapper, roomRepository);
        handler.execute();
    }
}
