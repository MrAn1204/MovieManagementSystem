package com.mms.mms_api.business.handler.room;

import org.springframework.stereotype.Component;

import java.util.Objects;

import com.mms.mms_api.business.command.room.RoomUpdateCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.room.RoomDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;

/**
 * Handles room update commands.
 */
@Component
public class RoomUpdateHandler extends RoomBaseHandler<RoomUpdateCommand, RoomDetailDto> {
    /**
     * Creates a RoomUpdateHandler.
     *
     * @param roomMapper room mapper
     * @param roomRepository room repository
     */
    public RoomUpdateHandler(RoomMapper roomMapper, RoomRepository roomRepository) {
        super(roomMapper, roomRepository);
    }

    /**
     * Updates an existing room with the given command data.
     *
     * @param request room update command
     * @return updated room detail DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the room does not exist
     */
    @Override
    public RoomDetailDto execute(RoomUpdateCommand request) {
        Room room = roomRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("room.notFound"));

        roomMapper.updateEntity(request, room);

        Room updatedRoom = roomRepository.save(Objects.requireNonNull(room));

        return roomMapper.toDetailDto(updatedRoom);
    }

}
