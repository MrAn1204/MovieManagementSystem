package com.mms.mms_api.business.command.seat;

import org.hibernate.validator.constraints.Range;

import com.mms.mms_api.business.command.BaseUpdateCommand;
import com.mms.mms_api.common.AppConstant;
import com.mms.mms_api.model.SeatType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class SeatUpdateCommand extends BaseUpdateCommand {
    @NotNull(message = "{seat.column.required}")
    @Range(min = 1, max = AppConstant.COLUMN_MAX, message = "{seat.column.invalid}")
    private int seatColumn;

    @NotNull(message = "{seat.row.required}")
    @Range(min = 1, max = AppConstant.ROW_MAX, message = "{seat.row.invalid}")
    private int seatRow;

    @NotNull(message = "{seat.type.required}")
    private SeatType seatType;

    @NotNull(message = "{seat.name.required}")
    private String name;
}
