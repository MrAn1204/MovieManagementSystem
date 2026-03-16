package com.mms.mms_api.business.command.schedule;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.lang.NonNull;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ScheduleUpdateCommand extends BaseUpdateCommand {
    private LocalDateTime showTime;

    @NotNull(message = "{schedule.movie.required}")
    @NonNull
    private UUID movieId;
    
    @NotNull(message = "{schedule.room.required}")
    @NonNull
    private UUID roomId;
}
