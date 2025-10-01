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
import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.business.query.ticket.TicketGetAllQuery;
import com.mms.mms_api.business.query.ticket.TicketGetByIdQuery;
import com.mms.mms_api.business.service.TicketService;
import com.mms.mms_api.dto.TicketDto;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<TicketDto> create(@RequestBody TicketCreateCommand command) {
        TicketDto ticket = ticketService.handle(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAll() {
        List<TicketDto> tickets = ticketService.handle(new TicketGetAllQuery());

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getById(@PathVariable UUID id) {
        TicketDto ticket = ticketService.handle(new TicketGetByIdQuery(id));

        return ResponseEntity.ok(ticket);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDto> update(@PathVariable UUID id, @RequestBody TicketUpdateCommand request) {
        request.setId(id);
        TicketDto ticket = ticketService.handle(request);

        return ResponseEntity.ok(ticket);
    }
}
