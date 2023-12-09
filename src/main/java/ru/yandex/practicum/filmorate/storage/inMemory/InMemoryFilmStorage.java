package ru.yandex.practicum.filmorate.storage.inMemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmPopularityComparator;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

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

    @Override
    public void addLike(int filmId, int userId) {
        Film film = films.get(filmId);
        Set<Integer> likes = film.getLikes(); // получем поле, хранящее лайки
        likes.add(userId); // добавляем лайк выбранному фильму
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        Film film = films.get(filmId);
        Set<Integer> likes = film.getLikes(); // получем поле, хранящее лайки
        likes.remove(userId); // удаляем лайк у выбранного фильму
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        List<Film> filmsList = new ArrayList<>(films.values()); // получаем список всех фильмов
        filmsList.sort(new FilmPopularityComparator().reversed()); // сортируем все фильмы по популярности
        List<Film> popularFilms = new ArrayList<>(); // список популярных фильмов
        if (filmsList.size() < count) {
            popularFilms.addAll(filmsList);
            return popularFilms;
        }
        for (int i = 0; i < count; i++) { // добавляем первые count популярных фильмов
            Film film = filmsList.get(i);
            popularFilms.add(film);
        }
        return popularFilms;
    }

    private int generateId() {
        return ++id;
    }
}
