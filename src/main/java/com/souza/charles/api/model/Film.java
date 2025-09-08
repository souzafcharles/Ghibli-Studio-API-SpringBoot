package com.souza.charles.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_film")
public class Film {
    @Id
    private String id;
    @Column(nullable = false)
    private String title;
    @Column(length = 5000)
    private String description;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("original_title_romanised")
    private String originalTitleRomanised;
    private String director;
    private String producer;
    @JsonProperty("release_date")
    private String releaseDate;
    private String image;

    public Film() {}

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getProducer() {
        return producer;
    }

    public String getDirector() {
        return director;
    }

    public String getOriginalTitleRomanised() {
        return originalTitleRomanised;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}