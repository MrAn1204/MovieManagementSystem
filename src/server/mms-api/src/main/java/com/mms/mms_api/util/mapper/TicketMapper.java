package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.ticket.TicketCreateCommand;
import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.dto.TicketDto;
import com.mms.mms_api.model.Ticket;

@Mapper(componentModel = "spring", uses = {ScheduleMapper.class, SeatMapper.class})
public interface TicketMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    @Mapping(target = "seat", ignore = true)
    Ticket toEntity(TicketCreateCommand command);

    TicketDto toDto(Ticket ticket);

    @Mapping(target = "schedule", ignore = true)
    @Mapping(target = "seat", ignore = true)
    void updateEntity(TicketUpdateCommand command, @MappingTarget Ticket ticket);
}
