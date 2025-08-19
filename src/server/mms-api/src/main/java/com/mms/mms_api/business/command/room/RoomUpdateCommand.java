package com.mms.mms_api.business.command.room;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class RoomUpdateCommand extends BaseUpdateCommand {
    private int seatQuantity;

    private String name;
}