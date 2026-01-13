package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.ticket.TicketCreateCommand;
import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.dto.TicketDto;
import com.mms.mms_api.model.Ticket;

@Mapper(config = DefaultMapperConfig.class, uses = { ScheduleMapper.class, SeatMapper.class })
public interface TicketMapper {
    @Mapping(target = "schedule", ignore = true)
    @Mapping(target = "seat", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "promotion", ignore = true)
    @Mapping(target = "user", ignore = true)
    Ticket toEntity(TicketCreateCommand command);

    @Mapping(target = "username", source = "ticket.user.username")
    @Mapping(target = "phoneNumber", source = "ticket.user.phoneNumber")
    @Mapping(target = "promotion.name", source = "ticket.promotion.title")
    TicketDto toDto(Ticket ticket);

    @Mapping(target = "schedule", ignore = true)
    @Mapping(target = "seat", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "promotion", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntity(TicketUpdateCommand command, @MappingTarget Ticket ticket);
}
