package com.mms.mms_api.business.command.room;

import com.mms.mms_api.business.command.BaseCreateCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class RoomCreateCommand extends BaseCreateCommand {
    private int seatQuantity;

    private String name;
}
