package ru.yandex.practicum.filmorate.storage.inMemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class); // создаем логер
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        if (film.getId() > 0) {
            log.debug("Фильм '{}' c id={} не может быть добавлен.", film.getName(), film.getId() + '\n' +
                    "Для добавления нового фильма id должен быть равен нулю");
            return null;
        }
        int filmId = generateId();
        film.setId(filmId);
        films.put(filmId, film);
        return film;
    }

    @Override
    public Film updateFilm(Film updatedFilm) {
        final int id = updatedFilm.getId();
        final Film savedFilm = films.get(id);
        if (savedFilm == null) {
            log.debug("Фильм '{}' c id={} не найден для обновления", updatedFilm.getName(), updatedFilm.getId());
            return null;
        }
        films.put(id, updatedFilm);
        return updatedFilm;
    }

    @Override
    public Film getFilm(int id) {
        final Film film = films.get(id);
        if (film == null) {
            log.debug("Фильм с id={} не найден в базе данных", id);
            return null;
        }
        return film;
    }

    private int generateId() {
        return ++id;
    }
}
