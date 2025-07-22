package com.mms.mms_api.business.command.movie;

import java.time.LocalDate;
import java.util.List;

import com.mms.mms_api.business.command.BaseCreateCommand;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieCreateCommand implements BaseCreateCommand {
    private String name;

    private LocalDate releaseDate;

    private int duration;

    private String content;

    private String thumbnail;

    List<String> genres;

    List<String> studios;

    List<String> talents;

    String language;
}
