package com.mms.mms_api.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.seat.SeatCreateCommand;
import com.mms.mms_api.business.command.seat.SeatDeleteCommand;
import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.business.handler.seat.SeatCreateHandler;
import com.mms.mms_api.business.handler.seat.SeatDeleteHandler;
import com.mms.mms_api.business.handler.seat.SeatGetAllHandler;
import com.mms.mms_api.business.handler.seat.SeatGetByIdHandler;
import com.mms.mms_api.business.handler.seat.SeatTypeGetAllHandler;
import com.mms.mms_api.business.handler.seat.SeatUpdateHandler;
import com.mms.mms_api.business.query.seat.SeatGetAllQuery;
import com.mms.mms_api.business.query.seat.SeatGetByIdQuery;
import com.mms.mms_api.business.query.seat.SeatTypeGetAllQuery;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.seat.SeatDto;
import com.mms.mms_api.util.mapper.SeatMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    private final RoomRepository roomRepository;

    private final ScheduleSeatRepository scheduleSeatRepository;

    private final SeatMapper seatMapper;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public SeatDto handle(SeatCreateCommand request) {
        SeatCreateHandler handler = new SeatCreateHandler(request, seatMapper, seatRepository, roomRepository);
        return handler.execute();
    }

    public List<SeatDto> handle(SeatGetAllQuery request) {
        SeatGetAllHandler handler = new SeatGetAllHandler(request, seatMapper, seatRepository); 
        return handler.execute();
    }

    public SeatDto handle(SeatGetByIdQuery request) {
        SeatGetByIdHandler handler = new SeatGetByIdHandler(request, seatMapper, seatRepository);
        return handler.execute();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public SeatDto handle(SeatUpdateCommand request) {
        SeatUpdateHandler handler = new SeatUpdateHandler(request, seatMapper, seatRepository);
        return handler.execute();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void handle(SeatDeleteCommand request) {
        SeatDeleteHandler handler = new SeatDeleteHandler(request, seatMapper, seatRepository, scheduleSeatRepository);
        handler.execute();
    }

    public Map<String, Double> handle(SeatTypeGetAllQuery request) {
        SeatTypeGetAllHandler handler = new SeatTypeGetAllHandler(request);
        return handler.execute();
    }
}
