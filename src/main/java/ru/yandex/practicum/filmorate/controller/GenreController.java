package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private static final Logger log = LoggerFactory.getLogger(GenreController.class);
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreServiceArg) {
        genreService = genreServiceArg;
    }

    @GetMapping
    public List<Genre> getGenres() {
        log.debug("Пришел GET /genres запрос");
        final List<Genre> genresList = genreService.getGenres();
        log.debug("На запрос GET /genres отправлен ответ с размером тела: {}", genresList.size());
        return genresList;
    }

    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable int id) {
        log.debug("Пришел GET /genres/{} запрос", id);
        final Genre genre = genreService.getGenre(id);
        log.debug("На запрос GET /genres/{} отправлен ответ с размером тела: 1", id);
        return genre;
    }
}
