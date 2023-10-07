package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmCreateException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.FilmValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired // внедряем зависимость через конструктор
    public FilmService(FilmStorage filmStorageArg, UserStorage userStorageArg) {
        filmStorage = filmStorageArg;
        userStorage = userStorageArg;
    }

    public Film createFilm(Film film) {
        FilmValidation.validate(film);
        Film createdFilm = filmStorage.createFilm(film);
        if (createdFilm == null) {
            throw new FilmCreateException(String.format("Фильм с id=%d не может быть добавлен в базу данных. " +
                    "Для добавления нового фильма id должен быть равен нулю.!", film.getId()));
        }
        return createdFilm;
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
        Set<Integer> likes = film.getLikes(); // получем поле, хранящее лайки
        likes.add(userId); // добавляем лайк выбранному фильму
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
        Set<Integer> likes = film.getLikes();
        likes.remove(userId); // удаляем лайк у выбранного фильму
    }

    public List<Film> getPopularFilms(int count) {
        List<Film> popularFilms = new ArrayList<>(); // список популярных фильмов
        List<Film> filmsList = filmStorage.getFilms(); // получаем список всех фильмов
        filmsList.sort(new FilmPopularityComparator().reversed()); // сортируем все фильмы по популярности
        if (filmsList.size() < count) { // если фильмов в базе меньше, чем запрошено, то выводим все имеющиеся фильмы
            popularFilms.addAll(filmsList);
            return popularFilms;
        }
        for (int i = 0; i < count; i++) { // добавляем первые count популярных фильмов
            Film film = filmsList.get(i);
            popularFilms.add(film);
        }
        return popularFilms;
    }
}
