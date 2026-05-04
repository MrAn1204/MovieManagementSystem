package com.mms.mms_api.dto.movie;

import java.util.List;

import com.mms.mms_api.dto.schedule.ScheduleSummaryDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieDetailDto extends MovieDto {
    private String thumbnail;

    private String content;

    private List<ScheduleSummaryDto> schedules;
}
