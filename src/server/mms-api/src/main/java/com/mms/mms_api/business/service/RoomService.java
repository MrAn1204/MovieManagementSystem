package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.room.RoomCreateCommand;
import com.mms.mms_api.business.command.room.RoomDeleteCommand;
import com.mms.mms_api.business.command.room.RoomUpdateCommand;
import com.mms.mms_api.business.query.room.RoomGetAllQuery;
import com.mms.mms_api.business.query.room.RoomGetByIdQuery;
import com.mms.mms_api.business.query.room.RoomSearchQuery;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.room.RoomDetailDto;
import com.mms.mms_api.dto.room.RoomDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.RoomValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomService {
    private final RequestMediator mediator;
    private final RoomValidator roomValidator;

    @PreAuthorize("hasAuthority('ADMIN')")
    public RoomDetailDto handle(RoomCreateCommand request) {
        roomValidator.validate(request);
        return mediator.execute(request);
    }

    public List<RoomDto> handle(RoomGetAllQuery request) {
        return mediator.execute(request);
    }

    public RoomDetailDto handle(RoomGetByIdQuery request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public RoomDetailDto handle(RoomUpdateCommand request) {
        roomValidator.validate(request);
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void handle(RoomDeleteCommand request) {
        mediator.execute(request);
    }

    public PaginatedResult<RoomDto> handle(RoomSearchQuery request) {
        return mediator.execute(request);
    }
}
