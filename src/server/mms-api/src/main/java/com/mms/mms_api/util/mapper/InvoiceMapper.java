package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.invoice.InvoiceCreateCommand;
import com.mms.mms_api.business.command.invoice.InvoiceUpdateCommand;
import com.mms.mms_api.dto.InvoiceDto;
import com.mms.mms_api.model.Invoice;

@Mapper(config = DefaultMapperConfig.class, uses = { TicketMapper.class })
public interface InvoiceMapper {
    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "user", ignore = true)
    Invoice toEntity(InvoiceCreateCommand command);

    InvoiceDto toDto(Invoice invoice);

    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(InvoiceUpdateCommand command, @MappingTarget Invoice invoice);
}
