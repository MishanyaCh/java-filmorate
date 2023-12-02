package ru.yandex.practicum.filmorate.storage.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public class GenreDBStorage {
    private static final Logger log = LoggerFactory.getLogger(GenreDBStorage.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDBStorage(JdbcTemplate jdbcTemplateArg) {
        jdbcTemplate = jdbcTemplateArg;
    }

    public List<Genre> getGenres() {
        String qslQuery = "SELECT * FROM genre";
        return jdbcTemplate.query(qslQuery, new GenreRowMapper());
    }

    public Genre getGenre(int id) {
        String qslQuery = "SELECT * FROM genre WHERE id = ?";
        List<Genre> genres = jdbcTemplate.query(qslQuery, new GenreRowMapper(), id);
        if (genres.isEmpty()) {
            log.debug("Жанр с id={} не найден в базе данных.", id);
            return null;
        }
        return genres.get(0);
    }
}
