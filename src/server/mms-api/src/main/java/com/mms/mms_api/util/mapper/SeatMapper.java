package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.mms.mms_api.business.command.seat.SeatCreateCommand;
import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.dto.SeatDto;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.Seat;

@Mapper(config = DefaultMapperConfig.class)
public interface SeatMapper {
    @Mapping(target = "room", ignore = true)
    @Mapping(target = "scheduleSeats", ignore = true)
    @Mapping(target = "linkedSeat", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    Seat toEntity(SeatCreateCommand command);

    @Mapping(target = "linkedSeatId", expression = "java(seat.getLinkedSeat() != null ? seat.getLinkedSeat().getId() : null)")
    @Mapping(target = "reserved", ignore = true)
    SeatDto toDto(Seat seat);


    default SeatDto toDto(ScheduleSeat scheduleSeat) {
        SeatDto seatDto = toDto(scheduleSeat.getSeat());
        seatDto.setReserved(scheduleSeat.isReserved());
        return seatDto;
    }

    @Mapping(target = "room", ignore = true)
    @Mapping(target = "scheduleSeats", ignore = true)
    @Mapping(target = "linkedSeat", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    void updateEntity(SeatUpdateCommand command, @MappingTarget Seat seat);

    default Seat mapLinkedSeat(Seat mainSeat, Seat secondSeat) {
        if (secondSeat == null) {
            secondSeat = new Seat();
        }

        secondSeat.setName(mainSeat.getName());
        secondSeat.setSeatType(mainSeat.getSeatType());
        secondSeat.setSeatColumn(mainSeat.getSeatColumn() + 1);
        secondSeat.setSeatRow(mainSeat.getSeatRow());
        secondSeat.setRoom(mainSeat.getRoom());
        secondSeat.setLinkedSeat(mainSeat);

        return secondSeat;
    }
}