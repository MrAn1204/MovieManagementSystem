package com.mms.mms_api.business.handler.room;

import org.springframework.stereotype.Component;

import java.util.Objects;

import com.mms.mms_api.business.command.room.RoomCreateCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.room.RoomDetailDto;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;

/**
 * Handles room creation commands.
 */
@Component
public class RoomCreateHandler extends RoomBaseHandler<RoomCreateCommand, RoomDetailDto> {
    /**
     * Creates a RoomCreateHandler.
     *
     * @param roomMapper room mapper
     * @param roomRepository room repository
     */
    public RoomCreateHandler(RoomMapper roomMapper, RoomRepository roomRepository) {
        super(roomMapper, roomRepository);
    }

    /**
     * Creates a new room and persists it.
     *
     * @param request room create command
     * @return created room detail DTO
     */
    @Override
    public RoomDetailDto execute(RoomCreateCommand request) {
        Room room = roomMapper.toEntity(request);
        
        Room savedRoom = roomRepository.save(Objects.requireNonNull(room));

        return roomMapper.toDetailDto(savedRoom);
    }
    
}
