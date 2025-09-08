package com.souza.charles.api.model.dto;

import com.souza.charles.api.model.entity.Film;
import java.io.Serializable;

public record FilmResponseDTO(
        String id,
        String title,
        String description,
        String originalTitle,
        String originalTitleRomanised,
        String director,
        String producer,
        String releaseDate,
        String image
) implements Serializable {
    public FilmResponseDTO(Film film) {
        this(
                film.getId(),
                film.getTitle(),
                film.getDescription(),
                film.getOriginalTitle(),
                film.getOriginalTitleRomanised(),
                film.getDirector(),
                film.getProducer(),
                film.getReleaseDate(),
                film.getImage()
        );
    }
}
