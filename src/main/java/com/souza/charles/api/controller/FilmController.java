package com.souza.charles.api.controller;

import com.souza.charles.api.model.dto.FilmRequestDTO;
import com.souza.charles.api.model.dto.FilmResponseDTO;
import com.souza.charles.api.service.FilmService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public ResponseEntity<FilmResponseDTO> create(@RequestBody @Valid FilmRequestDTO dto) {
        FilmResponseDTO created = filmService.createFilm(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(filmService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmResponseDTO> update(@PathVariable String id, @RequestBody @Valid FilmRequestDTO dto) {
        return ResponseEntity.ok(filmService.updateFilm(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<FilmResponseDTO>>> getAll(
            Pageable pageable,
            PagedResourcesAssembler<FilmResponseDTO> assembler
    ) {
        var page = filmService.findAllFilms(pageable);

        var model = assembler.toModel(page, film ->
                EntityModel.of(film,
                        WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(FilmController.class).getById(film.id())
                        ).withSelfRel()
                )
        );
        return page.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(model);
    }

    @GetMapping("/title")
    public ResponseEntity<List<FilmResponseDTO>> getByTitle(@RequestParam("q") String title) {
        return ResponseEntity.ok(filmService.findFilmsByTitle(title));
    }

    @GetMapping("/directors")
    public ResponseEntity<Set<String>> getDirectors() {
        return ResponseEntity.ok(filmService.findDirectors());
    }

    @GetMapping("/newest")
    public ResponseEntity<List<FilmResponseDTO>> getNewest() {
        return ResponseEntity.ok(filmService.findFilmsByNewest());
    }

    @GetMapping("/search")
    public ResponseEntity<List<FilmResponseDTO>> getByYearRange(@RequestParam String start, @RequestParam String end) {
        return ResponseEntity.ok(filmService.findFilmsByYearRange(start, end));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> statsByDirector() {
        return ResponseEntity.ok(filmService.getStatsByDirector());
    }

    @GetMapping("/average-release")
    public ResponseEntity<Double> averageReleaseYear() {
        return ResponseEntity.ok(filmService.getAverageReleaseYear());
    }

    @GetMapping("/producers")
    public ResponseEntity<List<Map.Entry<String, Long>>> producersByFilmCount() {
        return ResponseEntity.ok(filmService.getProducersByFilmCount());
    }
}