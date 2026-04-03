package com.mms.mms_api.util.mapper;

import java.time.LocalDateTime;

import org.mapstruct.AfterMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.ticket.TicketCreateCommand;
import com.mms.mms_api.business.command.ticket.TicketUpdateCommand;
import com.mms.mms_api.dto.ticket.TicketDetailDto;
import com.mms.mms_api.dto.ticket.TicketDto;
import com.mms.mms_api.model.Ticket;

@Mapper(config = DefaultMapperConfig.class, uses = { ScheduleMapper.class, SeatMapper.class })
public interface TicketMapper {
    @Mapping(target = "schedule", ignore = true)
    @Mapping(target = "seat", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "promotion", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "price", ignore = true)
    Ticket toEntity(TicketCreateCommand command);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "movie", source = "ticket.schedule.movie")
    @Mapping(target = "paid", expression = "java(ticket.getInvoice() != null)")
    TicketDto toDto(Ticket ticket);
    
    @InheritConfiguration(name = "toDto")
    @Mapping(target = "promotion.name", source = "ticket.promotion.title")
    TicketDetailDto toDetailDto(Ticket ticket);

    @Mapping(target = "schedule", ignore = true)
    @Mapping(target = "seat", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "promotion", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "price", ignore = true)
    void updateEntity(TicketUpdateCommand command, @MappingTarget Ticket ticket);

    @AfterMapping
    default void mapName(Ticket ticket, @MappingTarget TicketDto dto) {
        String code = ticket.getId().toString().toUpperCase().substring(0, 8);

        LocalDateTime showTime = ticket.getSchedule().getShowTime();

        String date = showTime.toLocalDate().toString().replace("-", "");

        String time = showTime.toLocalTime().toString().replace(":", "");

        String name = "TCK-" + code + "-" + date + time;

        dto.setName(name);
    }
}
