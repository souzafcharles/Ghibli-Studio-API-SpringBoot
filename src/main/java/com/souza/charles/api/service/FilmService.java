package com.souza.charles.api.service;

import com.souza.charles.api.model.dto.FilmRequestDTO;
import com.souza.charles.api.model.dto.FilmResponseDTO;
import com.souza.charles.api.model.entity.Film;
import com.souza.charles.api.repository.FilmRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final WebClient webClient;

    public FilmService(FilmRepository filmRepository, WebClient webClient) {
        this.filmRepository = filmRepository;
        this.webClient = webClient;
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

    public FilmResponseDTO createFilm(FilmRequestDTO dto) {
        Film film = new Film();
        film.setTitle(dto.title());
        film.setDescription(dto.description());
        film.setOriginalTitle(dto.originalTitle());
        film.setOriginalTitleRomanised(dto.originalTitleRomanised());
        film.setDirector(dto.director());
        film.setProducer(dto.producer());
        film.setReleaseDate(dto.releaseDate());
        film.setImage(dto.image());

        Film saved = filmRepository.save(film);
        return new FilmResponseDTO(saved);
    }

    public FilmResponseDTO updateFilm(String id, FilmRequestDTO dto) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Filme não encontrado: " + id));

        film.setTitle(dto.title());
        film.setDescription(dto.description());
        film.setOriginalTitle(dto.originalTitle());
        film.setOriginalTitleRomanised(dto.originalTitleRomanised());
        film.setDirector(dto.director());
        film.setProducer(dto.producer());
        film.setReleaseDate(dto.releaseDate());
        film.setImage(dto.image());

        return new FilmResponseDTO(filmRepository.save(film));
    }

    public void deleteFilm(String id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Filme não encontrado: " + id));
        filmRepository.delete(film);
    }

    public FilmResponseDTO findById(String id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Filme não encontrado: " + id));
        return new FilmResponseDTO(film);
    }

    public Page<FilmResponseDTO> findAllFilms(Pageable pageable) {
        return filmRepository.findAll(pageable)
                .map(FilmResponseDTO::new);
    }

    public List<FilmResponseDTO> findFilmsByTitle(String title) {
        return filmRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(FilmResponseDTO::new)
                .toList();
    }

    public List<FilmResponseDTO> findFilmsByDirector(String director) {
        return filmRepository.findByDirectorContainingIgnoreCase(director)
                .stream()
                .map(FilmResponseDTO::new)
                .toList();
    }

    public List<FilmResponseDTO> findFilmsByNewest() {
        return filmRepository.findAllByOrderByReleaseDateDesc()
                .stream()
                .map(FilmResponseDTO::new)
                .toList();
    }

    public List<FilmResponseDTO> findFilmsByYearRange(String startYear, String endYear) {
        return filmRepository.findByReleaseDateBetween(startYear, endYear)
                .stream()
                .map(FilmResponseDTO::new)
                .toList();
    }

    public Map<String, Long> getStatsByDirector() {
        return filmRepository.findAll().stream()
                .collect(Collectors.groupingBy(Film::getDirector, Collectors.counting()));
    }

    public Double getAverageReleaseYear() {
        return filmRepository.findAll().stream()
                .mapToInt(f -> Integer.parseInt(f.getReleaseDate()))
                .average()
                .orElse(0.0);
    }

    public List<Map.Entry<String, Long>> getProducersByFilmCount() {
        return filmRepository.findAll().stream()
                .collect(Collectors.groupingBy(Film::getProducer, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .toList();
    }

    public Set<String> findDirectors() {
        return filmRepository.findAll().stream()
                .map(Film::getDirector)
                .collect(Collectors.toSet());
    }
}