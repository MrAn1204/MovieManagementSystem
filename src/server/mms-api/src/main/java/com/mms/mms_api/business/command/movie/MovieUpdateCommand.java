package com.mms.mms_api.business.command.movie;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class MovieUpdateCommand extends BaseUpdateCommand {
    @NotNull(message = "{movie.name.required}")
    private String name;

    private LocalDate releaseDate;

    private int duration;

    private String content;

    private String thumbnail;

    private List<@NotNull(message = "{movie.genres.invalid}") UUID> genres;

    private List<@NotNull(message = "{movie.studios.invalid}") UUID> studios;

    private List<@NotNull(message = "{movie.talents.invalid}") UUID> talents;

    private UUID language;
}