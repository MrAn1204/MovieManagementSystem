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
import com.mms.mms_api.dto.RoomDetailDto;
import com.mms.mms_api.dto.RoomDto;
import com.mms.mms_api.util.validator.RoomValidator;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/rooms")
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;

    private final RoomValidator roomValidator;

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAll() {
        List<RoomDto> rooms = roomService.handle(new RoomGetAllQuery());
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDetailDto> getById(@PathVariable UUID id) {
        RoomDetailDto room = roomService.handle(new RoomGetByIdQuery(id));
        return ResponseEntity.ok(room);
    }

    @PostMapping("/create")
    public ResponseEntity<RoomDto> create(@RequestBody @Valid RoomCreateCommand request) {
        roomValidator.validate(request);
        RoomDto roomDto = roomService.handle(request);
        return ResponseEntity.ok(roomDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> update(@PathVariable UUID id, @RequestBody @Valid RoomUpdateCommand request) {
        request.setId(id);
        roomValidator.validate(request);
        RoomDto updatedRoom = roomService.handle(request);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        roomService.handle(new RoomDeleteCommand(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResult<RoomDto>> search(@Valid @RequestBody RoomSearchQuery request) {
        PaginatedResult<RoomDto> result = roomService.handle(request);

        return ResponseEntity.ok(result);
    }

}
