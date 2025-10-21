package com.mms.mms_api.business.command.room;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class RoomUpdateCommand extends BaseUpdateCommand {
    @NotNull(message = "{room.seatQuantity.required}")
    @PositiveOrZero(message = "{room.seatQuantity.invalid}")
    private int seatQuantity;

    @NotNull(message = "{room.name.required}")
    private String name;
}