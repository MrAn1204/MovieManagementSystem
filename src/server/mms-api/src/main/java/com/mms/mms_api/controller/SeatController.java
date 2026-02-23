package com.mms.mms_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.seat.SeatCreateCommand;
import com.mms.mms_api.business.command.seat.SeatDeleteCommand;
import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.business.query.seat.SeatGetAllQuery;
import com.mms.mms_api.business.query.seat.SeatGetByIdQuery;
import com.mms.mms_api.business.service.SeatService;
import com.mms.mms_api.dto.SeatDto;
import com.mms.mms_api.util.validator.SeatValidator;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/seats")
@AllArgsConstructor
public class SeatController {
    private final SeatService seatService;

    private final SeatValidator seatValidator;

    @PostMapping("/create")
    public ResponseEntity<SeatDto> create(@Valid @RequestBody SeatCreateCommand request) {
        seatValidator.validate(request);

        return ResponseEntity.ok(seatService.handle(request));
    }

    @GetMapping
    public ResponseEntity<List<SeatDto>> getAll() {
        List<SeatDto> seats = seatService.handle(new SeatGetAllQuery());
        return ResponseEntity.ok(seats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> getById(@PathVariable UUID id) {
        SeatDto seat = seatService.handle(new SeatGetByIdQuery(id));
        return ResponseEntity.ok(seat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatDto> update(@PathVariable UUID id, @Valid @RequestBody SeatUpdateCommand request) {
        request.setId(id);
        seatValidator.validate(request);

        SeatDto updatedSeat = seatService.handle(request);
        if (updatedSeat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSeat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        seatService.handle(new SeatDeleteCommand(id));
        return ResponseEntity.noContent().build();
    }
}