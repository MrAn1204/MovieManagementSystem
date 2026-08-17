package com.mms.mms_api.business.handler.ticket;

import org.springframework.stereotype.Component;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.mms.mms_api.business.query.ticket.TicketSearchQuery;
import com.mms.mms_api.business.specification.TicketSpecification;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.ticket.TicketDto;
import com.mms.mms_api.model.Ticket;
import com.mms.mms_api.util.SearchHelper;
import com.mms.mms_api.util.mapper.TicketMapper;

/**
 * Handles ticket search queries.
 */
@Component
public class TicketSearchHandler extends TicketBaseHandler<TicketSearchQuery, PaginatedResult<TicketDto>> {

    /**
     * Creates a TicketSearchHandler.
     *
     * @param ticketMapper ticket mapper
     * @param ticketRepository ticket repository
     */
    public TicketSearchHandler(TicketMapper ticketMapper,
            TicketRepository ticketRepository) {
        super(ticketMapper, ticketRepository);
    }

    /**
     * Executes a paginated ticket search using the supplied filters.
     *
     * @param request search query with filters and pagination parameters
     * @return paginated result of ticket DTOs
     */
    @Override
    public PaginatedResult<TicketDto> execute(TicketSearchQuery request) {
        Specification<Ticket> specification = new TicketSpecification(request);

        Page<Ticket> tickets = SearchHelper.getPage(request.getPageNumber(), request.getPageSize(),
                pageable -> ticketRepository.findAll(specification, pageable));

        return SearchHelper.getResult(tickets, ticketMapper::toDto);
    }

}
