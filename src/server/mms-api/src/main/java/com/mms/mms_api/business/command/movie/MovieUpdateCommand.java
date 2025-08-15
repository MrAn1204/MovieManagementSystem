package com.mms.mms_api.business.command.movie;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
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