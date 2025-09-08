package com.souza.charles.api.service;

import com.souza.charles.api.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class FilmService {

    @Autowired
    private WebClient webClient;

    private Film[] filmArray;

    public FilmService(WebClient webClient) {
        this.webClient = webClient;
        this.filmArray = fetchFilms();
    }

    private Film[] fetchFilms() {
        return webClient.get()
                .uri("/films")
                .retrieve()
                .bodyToMono(Film[].class)
                .block();
    }

    public Object[] findAllFilmsComplete() {
        return webClient.get()
                .uri("/films")
                .retrieve()
                .bodyToMono(Object[].class)
                .block();
    }

    public Film[] findAllFilms() {
        return filmArray;
    }

    public List<Film> findFilmsByTitle(String title) {
        List<Film> filmList = new ArrayList<>();
        for (Film film : filmArray) {
            String englishTitle = film.getTitle().toLowerCase();
            String romajiTitle = film.getOriginal_title_romanised().toLowerCase();
            if (englishTitle.contains(title.toLowerCase()) || romajiTitle.contains(title.toLowerCase())) {
                filmList.add(film);
            }
        }
        return filmList;
    }

    public Map<Integer, String> findDirectors() {
        Map<Integer, String> map = new HashMap<>();
        List<Film> filmList = Arrays.asList(filmArray);
        int mapKey = 1;
        for (Film film : filmList) {
            String director = film.getDirector();
            if (!map.containsValue(director)) {
                map.put(mapKey, director);
                mapKey++;
            }
        }
        return map;
    }

    public List<Film> findFilmsByNewest() {
        List<Film> filmList = Arrays.asList(filmArray);
        orderFilmsByReleaseDate(filmList);
        return filmList;
    }

    private List<Film> orderFilmsByReleaseDate(List<Film> list) {
        list.sort(Comparator.comparing(Film::getRelease_date));
        Collections.reverse(list);
        return list;
    }
}