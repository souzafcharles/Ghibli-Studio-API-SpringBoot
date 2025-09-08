package com.souza.charles.api.model.entity;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitleRomanised() {
        return originalTitleRomanised;
    }

    public void setOriginalTitleRomanised(String originalTitleRomanised) {
        this.originalTitleRomanised = originalTitleRomanised;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}