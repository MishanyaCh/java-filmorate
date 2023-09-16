package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class); // создаем логер для класса FilmController
    private static final Map<Integer, Film> films = new HashMap<>();
    private static final FilmValidation validator = new FilmValidation();
    private int id = 0;

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Количество фильмов в базе: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        validator.validate(film);
        if (film.getId() > 0) {
            log.debug("Фильм '{}' c id={} не может быть добавлен.", film.getName(), film.getId() + '\n' +
                    "Для добавления нового фильма id должен быть равен нулю");
            return null;
        }
        int filmId = generateId();
        film.setId(filmId);
        films.put(filmId, film);
        log.debug("Фильм '{}' c id={} успешно добавлен!", film.getName(), film.getId());
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film updatedFilm) {
        validator.validate(updatedFilm);
        final int id = updatedFilm.getId();
        final Film savedFilm = films.get(id);
        if (savedFilm == null) {
            log.debug("Фильм '{}' c id={} не найден для обновления", updatedFilm.getName(), updatedFilm.getId());
            throw new ValidationException("Фильм с id=" + id + " не найден для обновления");
        }
        films.put(id, updatedFilm);
        log.debug("Фильм '{}' c id={} успешно обновлен!", updatedFilm.getName(), updatedFilm.getId());
        return updatedFilm;
    }

    private int generateId() {
        return ++id;
    }
}
