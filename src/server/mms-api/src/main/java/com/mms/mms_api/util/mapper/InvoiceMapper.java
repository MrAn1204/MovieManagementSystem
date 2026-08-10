package com.mms.mms_api.util.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.invoice.InvoiceCreateCommand;
import com.mms.mms_api.business.command.invoice.InvoiceUpdateCommand;
import com.mms.mms_api.dto.invoice.InvoiceDetailDto;
import com.mms.mms_api.dto.invoice.InvoiceDto;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.model.Ticket;

/**
 * Mapper for invoice create/update commands and DTO projections.
 */
@Mapper(config = DefaultMapperConfig.class, uses = { TicketMapper.class })
public interface InvoiceMapper {
    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "totalMoney", ignore = true)
    @Mapping(target = "discount", expression = "java(command.getDiscount() / 100.0)")
    Invoice toEntity(InvoiceCreateCommand command);

    @Mapping(target = "name", ignore = true)
    InvoiceDto toDto(Invoice invoice);

    @Mapping(target = "name", ignore = true)
    InvoiceDetailDto toDetailDto(Invoice invoice);

    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "totalMoney", ignore = true)
    @Mapping(target = "discount", expression = "java(command.getDiscount() / 100.0)")
    void updateEntity(InvoiceUpdateCommand command, @MappingTarget Invoice invoice);

    default List<UUID> mapTickets(List<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty()) {
            return List.of();
        }

        return tickets.stream().map(ticket -> ticket == null ? null : ticket.getId()).toList();
    }

    @AfterMapping
    default void mapName(Invoice invoice, @MappingTarget InvoiceDto dto) {
        String code = invoice.getId().toString().toUpperCase().substring(0, 8);

        LocalDateTime createdDate = invoice.getCreatedAt();

        String name = "INV-" + code;

        if (createdDate != null) {
            String date = createdDate.toLocalDate().toString().replace("-", "");

            String time = createdDate.toLocalTime().toString().replace(":", "");

            name += "-" + date + time;
        }

        dto.setName(name);
    }
}
