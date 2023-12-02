package ru.yandex.practicum.filmorate.storage.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

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
        return null;
    }

    public List<Film> getFilms() {
        return null;
    }

    public Film getFilm(int id) {
        return null;
    }

    private void addFilmGenres(int filmId, Set<Genre> genres) {
        for (Genre genre: genres) {
            int genreId = genre.getId();
            String sqlQuery = "INSERT INTO films_genre (film_id, genre_id)" +
                    "VALUES (?, ?)";
            jdbcTemplate.update(sqlQuery, filmId, genreId);
        }
    }
}
