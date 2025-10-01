package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.seat.SeatCreateCommand;
import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.dto.SeatDto;
import com.mms.mms_api.model.Seat;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "scheduleSeats", ignore = true)
    @Mapping(target = "linkedSeat", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    Seat toEntity(SeatCreateCommand command);

    @Mapping(target = "roomName", source = "room.name")
    SeatDto toDto(Seat seat);

    @Mapping(target = "room", ignore = true)
    @Mapping(target = "scheduleSeats", ignore = true)
    @Mapping(target = "linkedSeat", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    void updateEntity(SeatUpdateCommand command, @MappingTarget Seat seat);

    default Seat mapSecondSeat(Seat seat) {
        Seat secondSeat = new Seat();

        secondSeat.setName(seat.getName());
        secondSeat.setSeatType(seat.getSeatType());
        secondSeat.setSeatColumn(seat.getSeatColumn() + 1);
        secondSeat.setSeatRow(seat.getSeatRow());
        secondSeat.setRoom(seat.getRoom());
        secondSeat.setLinkedSeat(seat.getId());

        return secondSeat;
    }   
}
