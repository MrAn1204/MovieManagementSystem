package com.mms.mms_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.ticket.TicketCreateCommand;
import com.mms.mms_api.business.service.TicketService;
import com.mms.mms_api.dto.TicketDto;

import lombok.AllArgsConstructor;

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
}
