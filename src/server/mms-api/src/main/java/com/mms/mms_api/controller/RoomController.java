package com.mms.mms_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.room.RoomCreateCommand;
import com.mms.mms_api.business.command.room.RoomDeleteCommand;
import com.mms.mms_api.business.command.room.RoomUpdateCommand;
import com.mms.mms_api.business.query.room.RoomGetAllQuery;
import com.mms.mms_api.business.query.room.RoomGetByIdQuery;
import com.mms.mms_api.business.query.room.RoomSearchQuery;
import com.mms.mms_api.business.service.RoomService;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.room.RoomDetailDto;
import com.mms.mms_api.dto.room.RoomDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Provides CRUD and search endpoints for rooms.
 */
@RestController
@RequestMapping("/api/rooms")
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;

    /**
     * Returns all rooms.
     *
     * @return list of rooms
     */
    @GetMapping
    public ResponseEntity<List<RoomDto>> getAll() {
        List<RoomDto> rooms = roomService.handle(new RoomGetAllQuery());
        return ResponseEntity.ok(rooms);
    }

    /**
     * Returns room details by id.
     *
     * @param id room identifier
     * @return room details
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoomDetailDto> getById(@PathVariable UUID id) {
        RoomDetailDto room = roomService.handle(new RoomGetByIdQuery(id));
        return ResponseEntity.ok(room);
    }

    /**
     * Creates a room.
     *
     * @param request create payload
     * @return created room details
     */
    @PostMapping("/create")
    public ResponseEntity<RoomDetailDto> create(@RequestBody @Valid RoomCreateCommand request) {
        RoomDetailDto roomDto = roomService.handle(request);
        return ResponseEntity.ok(roomDto);
    }

    /**
     * Updates a room by id.
     *
     * @param id room identifier
     * @param request update payload
     * @return updated room details
     */
    @PutMapping("/{id}")
    public ResponseEntity<RoomDetailDto> update(@PathVariable UUID id, @RequestBody @Valid RoomUpdateCommand request) {
        request.setId(id);
        RoomDetailDto updatedRoom = roomService.handle(request);
        return ResponseEntity.ok(updatedRoom);
    }

    /**
     * Deletes a room by id.
     *
     * @param id room identifier
     * @return no-content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        roomService.handle(new RoomDeleteCommand(id));
        return ResponseEntity.noContent().build();
    }

    /**
     * Searches rooms with filter and pagination.
     *
     * @param request search criteria
     * @return paginated room result
     */
    @PostMapping("/search")
    public ResponseEntity<PaginatedResult<RoomDto>> search(@Valid @RequestBody RoomSearchQuery request) {
        PaginatedResult<RoomDto> result = roomService.handle(request);

        return ResponseEntity.ok(result);
    }

}
