package com.mms.mms_api.business.command.room;

import com.mms.mms_api.business.command.BaseCreateCommand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class RoomCreateCommand extends BaseCreateCommand {
    @NotNull(message = "{room.seatQuantity.required}")
    private int seatQuantity;

    @NotNull(message = "{room.name.required}")
    private String name;
}
