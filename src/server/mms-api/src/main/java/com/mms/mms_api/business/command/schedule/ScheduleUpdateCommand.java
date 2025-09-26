package com.mms.mms_api.business.command.schedule;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ScheduleUpdateCommand extends BaseUpdateCommand {
    private LocalDateTime showTime;
    
    private UUID movieId;
    
    private UUID roomId;

    private Map<UUID, Boolean> seatStatuses;
}
