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

/**
 * Provides CRUD and search operations for rooms.
 */
@Service
@AllArgsConstructor
public class RoomService {
    private final RequestMediator mediator;
    private final RoomValidator roomValidator;

    /**
     * Creates a room.
     *
     * @param request create command
     * @return created room details
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public RoomDetailDto handle(RoomCreateCommand request) {
        roomValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Returns all rooms.
     *
     * @param request get-all query
     * @return list of room DTOs
     */
    public List<RoomDto> handle(RoomGetAllQuery request) {
        return mediator.execute(request);
    }

    /**
     * Returns room details by id.
     *
     * @param request get-by-id query
     * @return room detail DTO
     */
    public RoomDetailDto handle(RoomGetByIdQuery request) {
        return mediator.execute(request);
    }

    /**
     * Updates a room.
     *
     * @param request update command
     * @return updated room details
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public RoomDetailDto handle(RoomUpdateCommand request) {
        roomValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Deletes a room.
     *
     * @param request delete command
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void handle(RoomDeleteCommand request) {
        mediator.execute(request);
    }

    /**
     * Searches rooms with pagination.
     *
     * @param request search query
     * @return paginated room result
     */
    public PaginatedResult<RoomDto> handle(RoomSearchQuery request) {
        return mediator.execute(request);
    }
}
