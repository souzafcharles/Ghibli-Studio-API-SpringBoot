package com.souza.charles.api.service;

import com.souza.charles.api.model.Film;
import com.souza.charles.api.repository.FilmRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;
import java.util.*;

@Service
public class FilmService {

    private final WebClient webClient;
    private final FilmRepository filmRepository;

    public FilmService(WebClient webClient, FilmRepository filmRepository) {
        this.webClient = webClient;
        this.filmRepository = filmRepository;
    }

    @PostConstruct
    public void init() {
        if (filmRepository.count() == 0) {
            Film[] films = fetchFilmsFromApi();
            filmRepository.saveAll(Arrays.asList(films));
        }
    }

    private Film[] fetchFilmsFromApi() {
        return webClient.get()
                .uri("/films")
                .retrieve()
                .bodyToMono(Film[].class)
                .block();
    }

    public List<Film> findAllFilms() {
        return filmRepository.findAll();
    }

    public List<Film> findFilmsByTitle(String title) {
        return filmRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Film> findFilmsByDirector(String director) {
        return filmRepository.findByDirectorContainingIgnoreCase(director);
    }

    public List<Film> findFilmsByNewest() {
        return filmRepository.findAllByOrderByReleaseDateDesc();
    }

    public Set<String> findDirectors() {
        List<Film> films = filmRepository.findAll();
        Set<String> directors = new HashSet<>();
        for (Film film : films) {
            directors.add(film.getDirector());
        }
        return directors;
    }
}
