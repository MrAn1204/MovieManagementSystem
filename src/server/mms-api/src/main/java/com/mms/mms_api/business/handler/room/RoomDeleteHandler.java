package com.mms.mms_api.business.handler.room;

import org.springframework.stereotype.Component;

import java.util.UUID;

import com.mms.mms_api.business.command.room.RoomDeleteCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.util.mapper.RoomMapper;

@Component
public class RoomDeleteHandler extends RoomBaseHandler<RoomDeleteCommand, Void> {
    public RoomDeleteHandler(RoomMapper roomMapper, RoomRepository roomRepository) {
        super(roomMapper, roomRepository);
    }

    @Override
    public Void execute(RoomDeleteCommand request) {
        UUID id = request.getId();

        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("room.notFound");
        }

        roomRepository.deleteById(id);

        return null;
    }
}
