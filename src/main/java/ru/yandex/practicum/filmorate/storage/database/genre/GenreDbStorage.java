package ru.yandex.practicum.filmorate.storage.database.genre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Component
public class GenreDbStorage implements GenreStorage {
    private static final Logger log = LoggerFactory.getLogger(GenreDbStorage.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplateArg) {
        jdbcTemplate = jdbcTemplateArg;
    }

    @Override
    public List<Genre> getGenres() {
        String qslQuery = "SELECT * FROM genre";
        return jdbcTemplate.query(qslQuery, new GenreRowMapper());
    }

    @Override
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
