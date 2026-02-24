package com.mms.mms_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.ticket.TicketCreateCommand;
import com.mms.mms_api.business.command.ticket.TicketDeleteCommand;
import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.business.query.ticket.TicketGetAllQuery;
import com.mms.mms_api.business.query.ticket.TicketGetByIdQuery;
import com.mms.mms_api.business.query.ticket.TicketSearchQuery;
import com.mms.mms_api.business.service.TicketService;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.TicketDetailDto;
import com.mms.mms_api.dto.TicketDto;
import com.mms.mms_api.util.validator.TicketValidator;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    private final TicketValidator ticketValidator;

    @PostMapping("/create")
    public ResponseEntity<TicketDto> create(@Valid @RequestBody TicketCreateCommand request) {
        ticketValidator.validate(request);

        TicketDto ticket = ticketService.handle(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }

    @GetMapping
    public ResponseEntity<List<TicketDetailDto>> getAll() {
        List<TicketDetailDto> tickets = ticketService.handle(new TicketGetAllQuery());

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDetailDto> getById(@PathVariable UUID id) {
        TicketDetailDto ticket = ticketService.handle(new TicketGetByIdQuery(id));

        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDto> update(@PathVariable UUID id, @Valid @RequestBody TicketUpdateCommand request) {
        request.setId(id);
        ticketValidator.validate(request);

        TicketDto ticket = ticketService.handle(request);

        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        ticketService.handle(new TicketDeleteCommand(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResult<TicketDetailDto>> search(@Valid @RequestBody TicketSearchQuery request) {
        PaginatedResult<TicketDetailDto> result = ticketService.handle(request);
        return ResponseEntity.ok(result);
    }
}
