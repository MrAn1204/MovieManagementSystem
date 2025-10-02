package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.invoice.InvoiceCreateCommand;
import com.mms.mms_api.business.command.invoice.InvoiceDeleteCommand;
import com.mms.mms_api.business.command.invoice.InvoiceUpdateCommand;
import com.mms.mms_api.business.handler.invoice.InvoiceCreateHandler;
import com.mms.mms_api.business.handler.invoice.InvoiceDeleteHandler;
import com.mms.mms_api.business.handler.invoice.InvoiceGetAllHandler;
import com.mms.mms_api.business.handler.invoice.InvoiceGetByIdHandler;
import com.mms.mms_api.business.handler.invoice.InvoiceUpdateHandler;
import com.mms.mms_api.business.query.invoice.InvoiceGetAllQuery;
import com.mms.mms_api.business.query.invoice.InvoiceGetByIdQuery;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.dto.InvoiceDto;
import com.mms.mms_api.util.mapper.InvoiceMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    private final TicketRepository ticketRepository;

    private final InvoiceMapper invoiceMapper;

    public InvoiceDto handle(InvoiceCreateCommand request) {
        InvoiceCreateHandler handler = new InvoiceCreateHandler(request, invoiceMapper, invoiceRepository,
                ticketRepository);
        return handler.execute();

    }

    public List<InvoiceDto> handle(InvoiceGetAllQuery request) {
        InvoiceGetAllHandler handler = new InvoiceGetAllHandler(request, invoiceMapper, invoiceRepository);
        return handler.execute();
    }

    public InvoiceDto handle(InvoiceGetByIdQuery request) {
        InvoiceGetByIdHandler handler = new InvoiceGetByIdHandler(request, invoiceMapper, invoiceRepository);
        return handler.execute();
    }

    public InvoiceDto handle(InvoiceUpdateCommand request) {
        InvoiceUpdateHandler handler = new InvoiceUpdateHandler(request, invoiceMapper, invoiceRepository,
                ticketRepository);
        return handler.execute();
    }

    public void handle(InvoiceDeleteCommand request) {
        InvoiceDeleteHandler handler = new InvoiceDeleteHandler(request, invoiceRepository);
        handler.execute();
    }
}
