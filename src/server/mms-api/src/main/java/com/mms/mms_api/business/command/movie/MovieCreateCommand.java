package com.mms.mms_api.business.command.movie;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.mms.mms_api.business.command.BaseCreateCommand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class MovieCreateCommand extends BaseCreateCommand {
    @NotNull(message = "{movie.name.required}")
    private String name;

    private LocalDate releaseDate;

    private int duration;

    private String content;

    private String thumbnail;

    private List<UUID> genres;

    private List<UUID> studios;

    private List<UUID> talents;

    private UUID language;
}
