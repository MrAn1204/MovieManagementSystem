package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.schedule.ScheduleCreateCommand;
import com.mms.mms_api.business.command.schedule.ScheduleDeleteCommand;
import com.mms.mms_api.business.command.schedule.ScheduleUpdateCommand;
import com.mms.mms_api.business.handler.schedule.ScheduleCreateHandler;
import com.mms.mms_api.business.handler.schedule.ScheduleDeleteHandler;
import com.mms.mms_api.business.handler.schedule.ScheduleGetAllHandler;
import com.mms.mms_api.business.handler.schedule.ScheduleGetByIdHandler;
import com.mms.mms_api.business.handler.schedule.ScheduleUpdateHandler;
import com.mms.mms_api.business.query.schedule.ScheduleGetAllQuery;
import com.mms.mms_api.business.query.schedule.ScheduleGetByIdQuery;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.dto.ScheduleDto;
import com.mms.mms_api.util.mapper.ScheduleMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    public ScheduleDto handle(ScheduleCreateCommand request) {
        ScheduleCreateHandler handler = new ScheduleCreateHandler(request, scheduleMapper,
                scheduleRepository, movieRepository, roomRepository);
        return handler.execute();
    }

    public List<ScheduleDto> handle(ScheduleGetAllQuery request) {
        ScheduleGetAllHandler handler = new ScheduleGetAllHandler(request, scheduleMapper, scheduleRepository);
        return handler.execute();
    }

    public ScheduleDto handle(ScheduleGetByIdQuery request) {
        ScheduleGetByIdHandler handler = new ScheduleGetByIdHandler(request, scheduleMapper, scheduleRepository);
        return handler.execute();
    }

    public ScheduleDto handle(ScheduleUpdateCommand request) {
        ScheduleUpdateHandler handler = new ScheduleUpdateHandler(request, scheduleMapper, scheduleRepository,
                movieRepository, roomRepository);
        return handler.execute();
    }

    public void handle(ScheduleDeleteCommand request) {
        ScheduleDeleteHandler handler = new ScheduleDeleteHandler(request, scheduleRepository);
        handler.execute();
    }
}
