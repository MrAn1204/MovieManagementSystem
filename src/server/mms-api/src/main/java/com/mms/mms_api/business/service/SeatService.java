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
import com.mms.mms_api.dto.seat.SeatDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.SeatValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SeatService {
    private final RequestMediator mediator;
    private final SeatValidator seatValidator;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public SeatDto handle(SeatCreateCommand request) {
        seatValidator.validate(request);
        return mediator.execute(request);
    }

    public List<SeatDto> handle(SeatGetAllQuery request) {
        return mediator.execute(request);
    }

    public SeatDto handle(SeatGetByIdQuery request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public SeatDto handle(SeatUpdateCommand request) {
        seatValidator.validate(request);
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void handle(SeatDeleteCommand request) {
        mediator.execute(request);
    }

    public Map<String, Double> handle(SeatTypeGetAllQuery request) {
        return mediator.execute(request);
    }
}
