package com.mms.mms_api.util.mapper;

import java.util.List;
import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.invoice.InvoiceCreateCommand;
import com.mms.mms_api.business.command.invoice.InvoiceUpdateCommand;
import com.mms.mms_api.dto.InvoiceDto;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.model.Ticket;

@Mapper(config = DefaultMapperConfig.class, uses = { TicketMapper.class })
public interface InvoiceMapper {
    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "user", ignore = true)
    Invoice toEntity(InvoiceCreateCommand command);

    InvoiceDto toDto(Invoice invoice);

    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(InvoiceUpdateCommand command, @MappingTarget Invoice invoice);

    default List<UUID> mapTickets(List<Ticket> tickets) {
        return tickets.stream().map(Ticket::getId).toList();
    }
}
