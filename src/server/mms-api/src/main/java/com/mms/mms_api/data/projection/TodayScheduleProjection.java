package com.mms.mms_api.data.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TodayScheduleProjection {
    UUID getId();

    String getMovieName();

    LocalDateTime getShowTime();

    String getRoomName();
}
