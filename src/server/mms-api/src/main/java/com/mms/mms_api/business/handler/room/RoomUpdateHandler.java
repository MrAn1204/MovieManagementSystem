package com.mms.mms_api.business.handler.room;

import com.mms.mms_api.business.command.room.RoomUpdateCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.dto.RoomDto;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.util.mapper.RoomMapper;
import com.mms.mms_api.util.validator.RoomValidator;

public class RoomUpdateHandler extends RoomBaseHandler<RoomUpdateCommand, RoomDto> {
    public RoomUpdateHandler(RoomUpdateCommand request, RoomMapper roomMapper, RoomRepository roomRepository) {
        super(request, roomMapper, roomRepository);
    }

    @Override
    public RoomDto execute() {
        Room room = roomRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.ROOM_NOT_FOUND));

        RoomValidator.validateSeatQuantity(request.getSeatQuantity());
        RoomValidator.validateName(request.getName());

        roomMapper.updateEntity(request, room);

        Room updatedRoom = roomRepository.save(room);

        return roomMapper.toDto(updatedRoom);
    }

}
