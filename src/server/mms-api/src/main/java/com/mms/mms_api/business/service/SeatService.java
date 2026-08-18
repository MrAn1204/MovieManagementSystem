package com.mms.mms_api.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.seat.SeatCreateCommand;
import com.mms.mms_api.business.command.seat.SeatDeleteCommand;
import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.business.query.seat.SeatGetAllQuery;
import com.mms.mms_api.business.query.seat.SeatGetByIdQuery;
import com.mms.mms_api.business.query.seat.SeatTypeGetAllQuery;
import com.mms.mms_api.dto.seat.SeatDetailDto;
import com.mms.mms_api.dto.seat.SeatDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.SeatValidator;

import lombok.AllArgsConstructor;

/**
 * Provides CRUD operations for seats and seat-type lookup.
 */
@Service
@AllArgsConstructor
public class SeatService {
    private final RequestMediator mediator;
    private final SeatValidator seatValidator;

    /**
     * Creates a seat.
     *
     * @param request create command
     * @return created seat DTO
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public SeatDetailDto handle(SeatCreateCommand request) {
        seatValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Returns all seats.
     *
     * @param request get-all query
     * @return list of seat DTOs
     */
    public List<SeatDto> handle(SeatGetAllQuery request) {
        return mediator.execute(request);
    }

    /**
     * Returns seat details by id.
     *
     * @param request get-by-id query
     * @return seat DTO
     */
    public SeatDetailDto handle(SeatGetByIdQuery request) {
        return mediator.execute(request);
    }

    /**
     * Updates a seat.
     *
     * @param request update command
     * @return updated seat DTO
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public SeatDetailDto handle(SeatUpdateCommand request) {
        seatValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Deletes a seat.
     *
     * @param request delete command
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void handle(SeatDeleteCommand request) {
        mediator.execute(request);
    }

    /**
     * Returns seat type multipliers.
     *
     * @param request seat-type query
     * @return seat type and multiplier mapping
     */
    public Map<String, Double> handle(SeatTypeGetAllQuery request) {
        return mediator.execute(request);
    }
}
