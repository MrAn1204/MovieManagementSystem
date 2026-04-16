package com.mms.mms_api.business.handler.room;

import org.springframework.stereotype.Component;

import java.util.UUID;

import com.mms.mms_api.business.command.room.RoomDeleteCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.util.mapper.RoomMapper;

/**
 * Handles room delete commands.
 */
@Component
public class RoomDeleteHandler extends RoomBaseHandler<RoomDeleteCommand, Void> {
    /**
     * Creates a RoomDeleteHandler.
     *
     * @param roomMapper room mapper
     * @param roomRepository room repository
     */
    public RoomDeleteHandler(RoomMapper roomMapper, RoomRepository roomRepository) {
        super(roomMapper, roomRepository);
    }

    /**
     * Deletes a room by its identifier.
     *
     * @param request delete command containing the target room id
     * @return {@code null}
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the room does not exist
     */
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
