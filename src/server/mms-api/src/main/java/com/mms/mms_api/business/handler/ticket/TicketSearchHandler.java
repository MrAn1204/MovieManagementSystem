package com.mms.mms_api.business.handler.ticket;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.mms.mms_api.business.query.ticket.TicketSearchQuery;
import com.mms.mms_api.business.specification.TicketSpecification;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.ticket.TicketDto;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.util.SearchHelper;
import com.mms.mms_api.util.mapper.TicketMapper;

public class TicketSearchHandler extends TicketBaseHandler<TicketSearchQuery, PaginatedResult<TicketDto>> {

    public TicketSearchHandler(TicketSearchQuery request, TicketMapper ticketMapper,
            TicketRepository ticketRepository) {
        super(request, ticketMapper, ticketRepository);
    }

    @Override
    public PaginatedResult<TicketDto> execute() {
        Pageable pageable = SearchHelper.generatePageable(request.getSortDirection(), request.getSortBy(),
                request.getPageNumber(), request.getPageSize());

        Specification<Ticket> specification = new TicketSpecification(request);

        Page<Ticket> tickets = ticketRepository.findAll(specification, pageable);

        List<TicketDto> ticketDtos = tickets.stream().map(ticketMapper::toDto).toList();

        return new PaginatedResult<>(ticketDtos, tickets.getTotalElements(), tickets.getTotalPages(),
                request.getPageSize(), request.getPageNumber());
    }

}
