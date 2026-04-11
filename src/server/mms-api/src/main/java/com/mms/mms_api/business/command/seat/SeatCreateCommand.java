package com.mms.mms_api.business.command.seat;

import java.util.UUID;

import org.hibernate.validator.constraints.Range;
import org.springframework.lang.NonNull;

import com.mms.mms_api.business.command.BaseCreateCommand;
import com.mms.mms_api.common.AppConstant;
import com.mms.mms_api.model.SeatType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Command payload for creating seats.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class SeatCreateCommand extends BaseCreateCommand {
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

    @NotNull(message = "{seat.room.required}")
    @NonNull
    private UUID roomId;
}
