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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TicketService {
    private final RequestMediator mediator;

    @Transactional
    public List<TicketDetailDto> handle(TicketCreateCommand request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<TicketDto> handle(TicketGetAllQuery request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN') || @ticketValidationService.isOwnedByUserId(#request.id, authentication.principal.id)")
    public TicketDetailDto handle(TicketGetByIdQuery request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public TicketDetailDto handle(TicketUpdateCommand request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void handle(TicketDeleteCommand request) {
        mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public PaginatedResult<TicketDto> handle(TicketSearchQuery request) {
        return mediator.execute(request);
    }
}
