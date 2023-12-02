package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.RatingMPA;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingMPARowMapper implements RowMapper<RatingMPA> {

    @Override
    public RatingMPA mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new RatingMPA(id, name);
    }
}
