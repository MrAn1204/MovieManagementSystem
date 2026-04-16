package com.mms.mms_api.business.command.movie;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

/**
 * Command payload for updating movies.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class MovieUpdateCommand extends BaseUpdateCommand {
    @NotNull(message = "{movie.name.required}")
    private String name;

    private LocalDate releaseDate;

    @Positive(message = "{movie.duration.invalid}")
    private int duration;

    private String content;

    private MultipartFile thumbnail;

    private List<@NotNull(message = "{movie.genres.invalid}") UUID> genreIds;

    private List<@NotNull(message = "{movie.studios.invalid}") UUID> studioIds;

    private List<@NotNull(message = "{movie.talents.invalid}") UUID> talentIds;

    private UUID languageId;
}