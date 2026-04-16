package com.mms.mms_api.business.command.room;

import com.mms.mms_api.business.command.BaseCreateCommand;
import com.mms.mms_api.common.AppConstant;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Command payload for creating rooms.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class RoomCreateCommand extends BaseCreateCommand {
    @NotNull(message = "{room.rowLength.required}")
    @PositiveOrZero(message = "{room.rowLength.invalid}")
    @Max(value = AppConstant.ROW_MAX, message = "{room.rowLength.max}")
    private int rowLength;

    @NotNull(message = "{room.columnLength.required}")
    @PositiveOrZero(message = "{room.columnLength.invalid}")
    @Max(value = AppConstant.COLUMN_MAX, message = "{room.columnLength.max}")
    private int columnLength;

    @NotNull(message = "{room.name.required}")
    private String name;
}
