package com.mms.mms_api.business.handler.room;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.room.RoomGetByIdQuery;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.room.RoomDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;

/**
 * Handles requests to retrieve room by id.
 */
@Component
public class RoomGetByIdHandler extends RoomBaseHandler<RoomGetByIdQuery, RoomDetailDto> {
    /**
     * Creates a RoomGetByIdHandler.
     *
     * @param roomMapper room mapper
     * @param roomRepository room repository
     */
    public RoomGetByIdHandler(RoomMapper roomMapper, RoomRepository roomRepository) {
        super(roomMapper, roomRepository);
    }

    /**
     * Retrieves a room by its identifier.
     *
     * @param request query containing the target room id
     * @return room detail DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the room does not exist
     */
    @Override
    public RoomDetailDto execute(RoomGetByIdQuery request) {
        Room room = roomRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("room.notFound"));

        return roomMapper.toDetailDto(room);
    }

}
