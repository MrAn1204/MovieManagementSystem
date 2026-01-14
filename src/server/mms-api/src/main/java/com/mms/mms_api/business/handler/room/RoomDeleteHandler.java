package com.mms.mms_api.business.handler.room;

import java.util.UUID;

import com.mms.mms_api.business.command.room.RoomDeleteCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.util.mapper.RoomMapper;

public class RoomDeleteHandler extends RoomBaseHandler<RoomDeleteCommand, Void> {
    public RoomDeleteHandler(RoomDeleteCommand request, RoomMapper roomMapper, RoomRepository roomRepository) {
        super(request, roomMapper, roomRepository);
    }

    @Override
    public Void execute() {
        UUID id = request.getId();

        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("room.notFound");
        }

        roomRepository.deleteById(id);

        return null;
    }
}
