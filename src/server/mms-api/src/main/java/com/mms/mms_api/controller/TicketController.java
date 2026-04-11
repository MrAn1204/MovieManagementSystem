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
import com.mms.mms_api.dto.ticket.TicketDetailDto;
import com.mms.mms_api.dto.ticket.TicketDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Provides CRUD and search endpoints for tickets.
 */
@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    /**
     * Creates one or more tickets from booking input.
     *
     * @param request create payload
     * @return created ticket details
     */
    @PostMapping("/create")
    public ResponseEntity<List<TicketDetailDto>> create(@Valid @RequestBody TicketCreateCommand request) {
        List<TicketDetailDto> tickets = ticketService.handle(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(tickets);
    }

    /**
     * Returns all tickets.
     *
     * @return list of tickets
     */
    @GetMapping
    public ResponseEntity<List<TicketDto>> getAll() {
        List<TicketDto> tickets = ticketService.handle(new TicketGetAllQuery());

        return ResponseEntity.ok(tickets);
    }

    /**
     * Returns ticket details by id.
     *
     * @param id ticket identifier
     * @return ticket details
     */
    @GetMapping("/{id}")
    public ResponseEntity<TicketDetailDto> getById(@PathVariable UUID id) {
        TicketDetailDto ticket = ticketService.handle(new TicketGetByIdQuery(id));

        return ResponseEntity.ok(ticket);
    }

    /**
     * Updates a ticket by id.
     *
     * @param id ticket identifier
     * @param request update payload
     * @return updated ticket details
     */
    @PutMapping("/{id}")
    public ResponseEntity<TicketDetailDto> update(@PathVariable UUID id, @Valid @RequestBody TicketUpdateCommand request) {
        request.setId(id);

        TicketDetailDto ticket = ticketService.handle(request);

        return ResponseEntity.ok(ticket);
    }

    /**
     * Deletes a ticket by id.
     *
     * @param id ticket identifier
     * @return no-content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        ticketService.handle(new TicketDeleteCommand(id));
        return ResponseEntity.noContent().build();
    }

    /**
     * Searches tickets with filter and pagination.
     *
     * @param request search criteria
     * @return paginated ticket result
     */
    @PostMapping("/search")
    public ResponseEntity<PaginatedResult<TicketDto>> search(@Valid @RequestBody TicketSearchQuery request) {
        PaginatedResult<TicketDto> result = ticketService.handle(request);
        return ResponseEntity.ok(result);
    }
}
