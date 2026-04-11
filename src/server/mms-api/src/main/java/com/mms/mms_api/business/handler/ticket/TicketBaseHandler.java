package com.mms.mms_api.business.handler.ticket;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.util.mapper.TicketMapper;

/**
 * Base handler for ticket-related requests.
 */
public abstract class TicketBaseHandler<I, O> extends BaseHandler<I, O> {
    protected TicketMapper ticketMapper;
    
    protected TicketRepository ticketRepository;
    
    /**
     * Creates a ticket base handler.
     *
     * @param ticketMapper ticket mapper
     * @param ticketRepository ticket repository
     */
    protected TicketBaseHandler(TicketMapper ticketMapper, TicketRepository ticketRepository) {
        this.ticketMapper = ticketMapper;
        this.ticketRepository = ticketRepository;
    }

}
