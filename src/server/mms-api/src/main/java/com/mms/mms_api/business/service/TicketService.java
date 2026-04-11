package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.ticket.TicketCreateCommand;
import com.mms.mms_api.business.command.ticket.TicketDeleteCommand;
import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.business.query.ticket.TicketGetAllQuery;
import com.mms.mms_api.business.query.ticket.TicketGetByIdQuery;
import com.mms.mms_api.business.query.ticket.TicketSearchQuery;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.ticket.TicketDetailDto;
import com.mms.mms_api.dto.ticket.TicketDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.TicketValidator;

import lombok.AllArgsConstructor;

/**
 * Provides CRUD and search operations for tickets.
 */
@Service
@AllArgsConstructor
public class TicketService {
    private final RequestMediator mediator;
    private final TicketValidator ticketValidator;

    /**
     * Creates ticket records for a booking request.
     *
     * @param request create command
     * @return created ticket details
     */
    @Transactional
    public List<TicketDetailDto> handle(TicketCreateCommand request) {
        ticketValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Returns all tickets.
     *
     * @param request get-all query
     * @return list of ticket DTOs
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<TicketDto> handle(TicketGetAllQuery request) {
        return mediator.execute(request);
    }

    /**
     * Returns ticket details by id.
     *
     * @param request get-by-id query
     * @return ticket detail DTO
     */
    @PreAuthorize("hasAuthority('ADMIN') || @ticketValidationService.isOwnedByUserId(#request.id, authentication.principal.id)")
    public TicketDetailDto handle(TicketGetByIdQuery request) {
        return mediator.execute(request);
    }

    /**
     * Updates a ticket.
     *
     * @param request update command
     * @return updated ticket details
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public TicketDetailDto handle(TicketUpdateCommand request) {
        ticketValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Deletes a ticket.
     *
     * @param request delete command
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void handle(TicketDeleteCommand request) {
        mediator.execute(request);
    }

    /**
     * Searches tickets with pagination.
     *
     * @param request search query
     * @return paginated ticket result
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public PaginatedResult<TicketDto> handle(TicketSearchQuery request) {
        return mediator.execute(request);
    }
}
