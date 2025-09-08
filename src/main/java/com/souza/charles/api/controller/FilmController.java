package com.souza.charles.api.controller;

import com.souza.charles.api.model.Film;
import com.souza.charles.api.service.FilmService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.findAllFilms();
    }

    @GetMapping("/title")
    public List<Film> getFilmsByTitle(@RequestParam("q") String title) {
        return filmService.findFilmsByTitle(title);
    }

    @GetMapping("/directors")
    public Set<String> getDirectors() {
        return filmService.findDirectors();
    }

    @GetMapping("/newest")
    public List<Film> getNewestFilms() {
        return filmService.findFilmsByNewest();
    }
}
