package com.mms.mms_api.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class MovieDto {
    private UUID id;

    private String name;

    private LocalDate releaseDate;

    private int duration;

    private String content;

    private String thumbnail;

    private double rating;

    List<String> genres;

    List<String> studios;

    List<String> talents;

    String language;
}