package com.souza.charles.api.service;

import com.souza.charles.api.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FilmService {

    @Autowired
    private RestTemplate template = new RestTemplate();

    Film[] filmArray = template.getForObject("https://ghibliapi.vercel.app/films/", Film[].class);

    public Object[] findAllFilmsComplete() {
        return template.getForObject("https://ghibliapi.vercel.app/films/", Object[].class);
    }

    public Film[] findAllFilms() {
        return filmArray;
    }

    public List<Film> findFilmsByTitle(String title) {
        List<Film> filmList = new ArrayList<>();
        for (int i = 0; i < filmArray.length; i++) {
            Film film = filmArray[i];
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
        list.sort(Comparator.comparing(film -> film.getRelease_date()));
        Collections.reverse(list);
        return list;
    }
}