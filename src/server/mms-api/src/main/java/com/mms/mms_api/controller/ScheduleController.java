package com.mms.mms_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.schedule.ScheduleCreateCommand;
import com.mms.mms_api.business.command.schedule.ScheduleDeleteCommand;
import com.mms.mms_api.business.command.schedule.ScheduleUpdateCommand;
import com.mms.mms_api.business.query.schedule.ScheduleGetAllQuery;
import com.mms.mms_api.business.query.schedule.ScheduleGetByIdQuery;
import com.mms.mms_api.business.query.schedule.ScheduleSearchQuery;
import com.mms.mms_api.business.service.ScheduleService;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.schedule.ScheduleDetailDto;
import com.mms.mms_api.dto.schedule.ScheduleDto;
import com.mms.mms_api.util.validator.ScheduleValidator;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/schedules")
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    private final ScheduleValidator scheduleValidator;

    @GetMapping
    public ResponseEntity<List<ScheduleDto>> getAll() {
        List<ScheduleDto> schedules = scheduleService.handle(new ScheduleGetAllQuery());
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDetailDto> getById(@PathVariable UUID id) {
        ScheduleDetailDto schedule = scheduleService.handle(new ScheduleGetByIdQuery(id));
        return ResponseEntity.ok(schedule);
    }

    @PostMapping("/create")
    public ResponseEntity<ScheduleDto> create(@Valid @RequestBody ScheduleCreateCommand request) {
        scheduleValidator.validate(request);

        ScheduleDto scheduleDto = scheduleService.handle(request);
        return ResponseEntity.ok(scheduleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> update(@PathVariable UUID id, @Valid @RequestBody ScheduleUpdateCommand request) {
        request.setId(id);
        scheduleValidator.validate(request);

        ScheduleDto updatedSchedule = scheduleService.handle(request);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        scheduleService.handle(new ScheduleDeleteCommand(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResult<ScheduleDto>> search(@Valid @RequestBody ScheduleSearchQuery request) {
        PaginatedResult<ScheduleDto> schedules = scheduleService.handle(request);
        return ResponseEntity.ok(schedules);
    }
    
}
