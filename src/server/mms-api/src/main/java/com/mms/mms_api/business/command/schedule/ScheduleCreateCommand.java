package com.mms.mms_api.business.command.schedule;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mms.mms_api.business.command.BaseCreateCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ScheduleCreateCommand extends BaseCreateCommand {
    private LocalDateTime showTime;
    
    private UUID movieId;
    
    private UUID roomId;
}
