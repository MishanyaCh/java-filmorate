package ru.yandex.practicum.filmorate.storage.database.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.RatingMPA;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component("FilmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private static final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplateArg) {
        jdbcTemplate = jdbcTemplateArg;
    }

    @Override
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

    @Override
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
        RatingMPA mpa = film.getMpa();
        int id = film.getId();
        Object[] args = {name, description, releaseDate, duration, mpa.getId(), id};
        jdbcTemplate.update(sqlQuery, args);
        updateFilmGenres(id, film.getGenres());
        return film;
    }

    @Override
    public List<Film> getFilms() {
        String sqlQuery = "SELECT * FROM films AS f " +
                "INNER JOIN rating_MPA AS r ON f.ratingMPA_id = r.id";
        List<Film> films = jdbcTemplate.query(sqlQuery, new FilmRowMapper());
        for (Film film : films) {
            int filmId = film.getId();
            List<Genre> genresList = getFilmGenres(filmId);
            Set<Genre> genres = film.getGenres();
            genres.addAll(genresList);
        }
        return films;
    }

    @Override
    public Film getFilm(int id) {
        String sqlQuery = "SELECT * FROM films AS f " +
                "INNER JOIN rating_MPA AS r ON f.ratingMPA_id = r.id " +
                "WHERE f.id = ?";
        List<Film> films = jdbcTemplate.query(sqlQuery, new FilmRowMapper(), id);
        if (films.isEmpty()) {
            log.debug("Фильм с id={} не найден в базе данных.", id);
            return null;
        }
        Film film = films.get(0);
        int filmId = film.getId();
        List<Genre> genresList = getFilmGenres(filmId);
        Set<Genre> genres = film.getGenres();
        genres.addAll(genresList);
        return film;
    }

    @Override
    public void addLike(int filmId, int userId) {
        String sqlQuery = "INSERT INTO likes (film_id, user_id) " +
                "VALUE (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        String sqlQuery = "DELETE FROM likes " +
                "WHERE user_id = ? AND film_id = ?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        String sqlQuery = "SELECT * " +
                "FROM (SELECT film_id, " +
                              "COUNT(DISTINCT user_id) AS count_likes" +
                       "FROM likes" +
                       "GROUP BY film_id" +
                       "ORDER BY count_likes DESC" +
                       "LIMIT ?) AS top_N_films " +
                "INNER JOIN films AS f ON top_N_films.film_id = f.id " +
                "INNER JOIN rating_MPA AS mpa ON f.ratingMPA_id = mpa.id";
        List<Film> popularFilms = jdbcTemplate.query(sqlQuery, new FilmRowMapper(), count);
        for (Film film : popularFilms) {
            int filmId = film.getId();
            List<Genre> genresList = getFilmGenres(filmId);
            Set<Genre> genres = film.getGenres();
            genres.addAll(genresList);
        }
        return popularFilms;
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

    private List<Genre> getFilmGenres(int filmId) {
        List<Genre> genres = new ArrayList<>();
        String sqlQuery = "SELECT f_g.genre_id, g.name " +
                "FROM films_genre AS f_g " +
                "INNER JOIN genre AS g ON f_g.genre_id = g.id " +
                "WHERE f_g.film_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, filmId);
        while (rowSet.next()) {
            int id = rowSet.getInt("genre_id");
            String name = rowSet.getString("name");
            Genre genre = new Genre(id, name);
            genres.add(genre);
        }
        return genres;
    }
}
