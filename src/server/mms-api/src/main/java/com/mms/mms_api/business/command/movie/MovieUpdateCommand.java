package com.mms.mms_api.business.command.movie;

import com.mms.mms_api.business.command.BaseUpdateCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MovieUpdateCommand extends BaseUpdateCommand {
    private String name;

    private LocalDate releaseDate;

    private int duration;

    private String content;

    private String thumbnail;

    private List<String> genres;

    private List<String> studios;

    private List<String> talents;

    private String language;
}