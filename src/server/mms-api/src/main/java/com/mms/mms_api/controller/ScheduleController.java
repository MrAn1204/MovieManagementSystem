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

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Provides CRUD and search endpoints for schedules.
 */
@RestController
@RequestMapping("/api/schedules")
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * Returns all schedules.
     *
     * @return list of schedules
     */
    @GetMapping
    public ResponseEntity<List<ScheduleDto>> getAll() {
        List<ScheduleDto> schedules = scheduleService.handle(new ScheduleGetAllQuery());
        return ResponseEntity.ok(schedules);
    }

    /**
     * Returns schedule details by id.
     *
     * @param id schedule identifier
     * @return schedule details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDetailDto> getById(@PathVariable UUID id) {
        ScheduleDetailDto schedule = scheduleService.handle(new ScheduleGetByIdQuery(id));
        return ResponseEntity.ok(schedule);
    }

    /**
     * Creates a schedule.
     *
     * @param request create payload
     * @return created schedule details
     */
    @PostMapping("/create")
    public ResponseEntity<ScheduleDetailDto> create(@Valid @RequestBody ScheduleCreateCommand request) {
        ScheduleDetailDto scheduleDto = scheduleService.handle(request);
        return ResponseEntity.ok(scheduleDto);
    }

    /**
     * Updates a schedule by id.
     *
     * @param id schedule identifier
     * @param request update payload
     * @return updated schedule details
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDetailDto> update(@PathVariable UUID id, @Valid @RequestBody ScheduleUpdateCommand request) {
        request.setId(id);

        ScheduleDetailDto updatedSchedule = scheduleService.handle(request);
        return ResponseEntity.ok(updatedSchedule);
    }

    /**
     * Deletes a schedule by id.
     *
     * @param id schedule identifier
     * @return no-content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        scheduleService.handle(new ScheduleDeleteCommand(id));
        return ResponseEntity.noContent().build();
    }

    /**
     * Searches schedules with filter and pagination.
     *
     * @param request search criteria
     * @return paginated schedule result
     */
    @PostMapping("/search")
    public ResponseEntity<PaginatedResult<ScheduleDto>> search(@Valid @RequestBody ScheduleSearchQuery request) {
        PaginatedResult<ScheduleDto> schedules = scheduleService.handle(request);
        return ResponseEntity.ok(schedules);
    }
    
}
