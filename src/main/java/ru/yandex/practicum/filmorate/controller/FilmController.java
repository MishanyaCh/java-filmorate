package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class); // создаем логер для класса FilmController
    private Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Пришел GET /films запрос");
        final List<Film> filmsList = new ArrayList<>(films.values());
        log.debug("На запрос GET /films отправлен ответ c размером тела: {}", filmsList.size());
        return filmsList;
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        FilmValidation.validate(film);
        log.debug("Пришел POST /films запрос с телом: {}", film);
        if (film.getId() > 0) {
            log.debug("Фильм '{}' c id={} не может быть добавлен.", film.getName(), film.getId() + '\n' +
                    "Для добавления нового фильма id должен быть равен нулю");
            return null;
        }
        int filmId = generateId();
        film.setId(filmId);
        films.put(filmId, film);
        log.debug("На запрос POST /films отправлен ответ c телом: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film updatedFilm) {
        FilmValidation.validate(updatedFilm);
        log.debug("Пришел PUT /films запрос с телом: {}", updatedFilm);
        final int id = updatedFilm.getId();
        final Film savedFilm = films.get(id);
        if (savedFilm == null) {
            log.debug("Фильм '{}' c id={} не найден для обновления", updatedFilm.getName(), updatedFilm.getId());
            throw new ValidationException("Фильм с id=" + id + " не найден для обновления");
        }
        films.put(id, updatedFilm);
        log.debug("На запрос PUT /films отправлен ответ c телом: {}", updatedFilm);
        return updatedFilm;
    }

    private int generateId() {
        return ++id;
    }
}
