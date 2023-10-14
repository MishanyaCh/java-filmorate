package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class); // создаем логер для класса FilmController
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmServiceArg) {
        filmService = filmServiceArg;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Пришел GET /films запрос");
        final List<Film> filmsList = filmService.getFilms();
        log.debug("На запрос GET /films отправлен ответ c размером тела: {}", filmsList.size());
        return filmsList;
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) {
        log.debug("Пришел GET /films/{} запрос", id);
        final Film film = filmService.getFilm(id);
        log.debug("На запрос GET /films/{} отправлен ответ c размером тела: 1 ", id);
        return film;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Film createFilm(@Valid @RequestBody Film film) {
        log.debug("Пришел POST /films запрос с телом: {}", film);
        final Film createdFilm = filmService.createFilm(film);
        log.debug("На запрос POST /films отправлен ответ c телом: {}", createdFilm);
        return createdFilm;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.debug("Пришел PUT /films запрос с телом: {}", film);
        final Film updatedFilm = filmService.updateFilm(film);
        log.debug("На запрос PUT /films отправлен ответ c телом: {}", updatedFilm);
        return updatedFilm;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.debug("Пришел PUT /films/{}/like/{} запрос", id, userId);
        filmService.addLike(id, userId);
        log.debug("Пользователь с id={} поставил лайк фильму с id={}", userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.debug("Пришел DELETE /films/{}/like/{} запрос", id, userId);
        filmService.deleteLike(id, userId);
        log.debug("Пользователь с id={} удалил лайк у фильма с id={}", userId, id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.debug("Пришел GET /films/popular?count={} запрос", count);
        final List<Film> popularFilmsList = filmService.getPopularFilms(count);
        log.debug("На запрос GET /films/popular?count={} отправлен ответ с размером тела: {}", count,
                popularFilmsList.size());
        return popularFilmsList;
    }
}
