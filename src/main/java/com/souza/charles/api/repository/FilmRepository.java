package com.souza.charles.api.repository;

import com.souza.charles.api.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, String> {

    List<Film> findByTitleContainingIgnoreCase(String title);

    List<Film> findByDirectorContainingIgnoreCase(String director);

    List<Film> findAllByOrderByReleaseDateDesc();
}
