package ru.yandex.practicum.filmorate.storage.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.RatingMPA;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class FilmDbStorage {
    private static final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplateArg) {
        jdbcTemplate = jdbcTemplateArg;
    }

    public Film createFilm(Film film) {
        String sqlQuery = "INSERT INTO films (name, description, releaseDate, duration, ratingMPA_id)" +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new FilmPreparedStatementCreator(sqlQuery, film), keyHolder);
        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        film.setId(id);
        addFilmGenres(id, film.getGenres());
        return film;
    }

    public Film updateFilm(Film film) {
        String sqlQuery = "UPDATE films SET " +
                "name = ?, " +
                "description = ?, " +
                "releaseDate = ?, " +
                "duration = ?, " +
                "ratingMPA_id = ? " +
                "WHERE id = ?";
        String name = film.getName();
        String description = film.getDescription();
        LocalDate releaseDate = film.getReleaseDate();
        int duration = film.getDuration();
        RatingMPA mpa = film.getRatingMPA();
        int id = film.getId();
        Object[] args = {name, description, releaseDate, duration, mpa.getId(), id};
        jdbcTemplate.update(sqlQuery, args);
        updateFilmGenres(id, film.getGenres());
        return film;
    }

    public List<Film> getFilms() {
        return null;
    }

    public Film getFilm(int id) {
        return null;
    }

    private void addFilmGenres(int filmId, Set<Genre> genres) {
        String sqlQuery = "INSERT INTO films_genre (film_id, genre_id) " +
                "VALUES (?, ?)";
        for (Genre genre : genres) {
            int genreId = genre.getId();
            jdbcTemplate.update(sqlQuery, filmId, genreId);
        }
    }

    private void updateFilmGenres(int filmId, Set<Genre> genres) {
        String sqlQuery = "UPDATE films_genre SET genre_id = ? " +
                "WHERE film_id = ?";
        for (Genre genre : genres) {
            int genreId = genre.getId();
            jdbcTemplate.update(sqlQuery, genreId, filmId);
        }
    }
}
