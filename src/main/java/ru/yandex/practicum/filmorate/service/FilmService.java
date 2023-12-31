package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.List;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired // внедряем зависимость через конструктор
    public FilmService(@Qualifier("FilmDbStorage") FilmStorage filmStorageArg,
                       @Qualifier("UserDbStorage") UserStorage userStorageArg) {
        filmStorage = filmStorageArg;
        userStorage = userStorageArg;
    }

    public Film createFilm(Film film) {
        FilmValidation.validate(film);
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        FilmValidation.validate(film);
        Film updatedFilm = filmStorage.updateFilm(film);
        if (updatedFilm == null) {
            throw new FilmNotFoundException(String.format("Фильм с id=%d отсутствует в базе данных. " +
                    "Для обновления данных о фильме сначала добавте фильм!", film.getId()));
        }
        return updatedFilm;
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilm(int filmId) {
        Film film = filmStorage.getFilm(filmId);
        if (film == null) {
            throw new FilmNotFoundException(String.format("Фильм с id=%d не найден в базе данных.", filmId));
        }
        return film;
    }

    public void addLike(int filmId, int userId) {
        Film film = filmStorage.getFilm(filmId); // находим фильм по id
        User user = userStorage.getUser(userId); // находим пользователя по id, который ставит лайк
        if (film == null) {
            throw new FilmNotFoundException(String.format("Фильм с id=%d не найден в базе данных.", filmId));
        }
        if (user == null) {
           throw new UserNotFoundException(String.format("Пользователь c id=%d не найден в базе данных.", userId));
        }
        filmStorage.addLike(filmId, userId); // добавляем лайк выбранному фильму
    }

    public void deleteLike(int filmId, int userId) {
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(userId);
        if (film == null) {
            throw new FilmNotFoundException(String.format("Фильм с id=%d не найден в базе данных.", filmId));
        }
        if (user == null) {
            throw new UserNotFoundException(String.format("Пользователь c id=%d не найден в базе данных.", userId));
        }
        filmStorage.deleteLike(filmId, userId); // удаляем лайк у выбранного фильма
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getPopularFilms(count);
    }
}
