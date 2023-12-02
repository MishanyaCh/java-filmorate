package ru.yandex.practicum.filmorate.storage.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.RatingMPA;

import java.util.List;

public class RatingMPADBStorage {
    private static final Logger log = LoggerFactory.getLogger(RatingMPADBStorage.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RatingMPADBStorage(JdbcTemplate jdbcTemplateArg) {
        jdbcTemplate = jdbcTemplateArg;
    }

    public List<RatingMPA> getRatingMPA() {
        String sqlQuery = "SELECT * FROM rating_MPA";
        return jdbcTemplate.query(sqlQuery, new RatingMPARowMapper());
    }

    public RatingMPA getRatingMPA(int id) {
        String sqlQuery = "SELECT * FROM rating_MPA WHERE id = ?";
        List<RatingMPA> ratingMPAList = jdbcTemplate.query(sqlQuery, new RatingMPARowMapper(), id);
        if (ratingMPAList.isEmpty()) {
            log.debug("Рейтинг MPA с id={} не найден в базе данных.", id);
            return null;
        }
        return ratingMPAList.get(0);
    }
}
