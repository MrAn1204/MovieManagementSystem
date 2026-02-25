package com.mms.mms_api.business.command.movie;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile thumbnail;

    private List<@NotNull(message = "{movie.genres.invalid}") UUID> genreIds;

    private List<@NotNull(message = "{movie.studios.invalid}") UUID> studioIds;

    private List<@NotNull(message = "{movie.talents.invalid}") UUID> talentIds;

    private UUID languageId;
}
