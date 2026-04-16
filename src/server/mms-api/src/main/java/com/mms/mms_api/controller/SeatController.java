package com.mms.mms_api.controller;

import java.util.List;
import java.util.Map;
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
import com.mms.mms_api.business.query.seat.SeatTypeGetAllQuery;
import com.mms.mms_api.business.service.SeatService;
import com.mms.mms_api.dto.seat.SeatDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Provides CRUD endpoints for seats and lookup endpoints for seat types.
 */
@RestController
@RequestMapping("/api/seats")
@AllArgsConstructor
public class SeatController {
    private final SeatService seatService;

    /**
     * Creates a seat.
     *
     * @param request create payload
     * @return created seat
     */
    @PostMapping("/create")
    public ResponseEntity<SeatDto> create(@Valid @RequestBody SeatCreateCommand request) {
        return ResponseEntity.ok(seatService.handle(request));
    }

    /**
     * Returns all seats.
     *
     * @return list of seats
     */
    @GetMapping
    public ResponseEntity<List<SeatDto>> getAll() {
        List<SeatDto> seats = seatService.handle(new SeatGetAllQuery());
        return ResponseEntity.ok(seats);
    }

    /**
     * Returns seat details by id.
     *
     * @param id seat identifier
     * @return seat details
     */
    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> getById(@PathVariable UUID id) {
        SeatDto seat = seatService.handle(new SeatGetByIdQuery(id));
        return ResponseEntity.ok(seat);
    }

    /**
     * Updates a seat by id.
     *
     * @param id seat identifier
     * @param request update payload
     * @return updated seat
     */
    @PutMapping("/{id}")
    public ResponseEntity<SeatDto> update(@PathVariable UUID id, @Valid @RequestBody SeatUpdateCommand request) {
        request.setId(id);

        SeatDto updatedSeat = seatService.handle(request);
        if (updatedSeat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSeat);
    }

    /**
     * Deletes a seat by id.
     *
     * @param id seat identifier
     * @return no-content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        seatService.handle(new SeatDeleteCommand(id));
        return ResponseEntity.noContent().build();
    }

    /**
     * Returns configured seat types and pricing multipliers.
     *
     * @return map of seat type name and multiplier
     */
    @GetMapping("/seat-types")
    public ResponseEntity<Map<String, Double>> getSeatTypes() {
        return ResponseEntity.ok(seatService.handle(new SeatTypeGetAllQuery()));
    }
    
}