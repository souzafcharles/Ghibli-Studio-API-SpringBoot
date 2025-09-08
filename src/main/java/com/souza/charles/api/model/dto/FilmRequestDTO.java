package com.souza.charles.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

public record FilmRequestDTO(
        @NotBlank(message = "The title is mandatory") String title,
        @NotBlank(message = "The description is mandatory") String description,
        String originalTitle,
        String originalTitleRomanised,
        String director,
        String producer,
        @NotBlank(message = "The release year is mandatory") String releaseDate,
        String image
) implements Serializable { }