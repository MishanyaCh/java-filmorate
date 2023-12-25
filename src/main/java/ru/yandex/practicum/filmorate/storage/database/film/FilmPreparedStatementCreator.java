package ru.yandex.practicum.filmorate.storage.database.film;

import org.springframework.jdbc.core.PreparedStatementCreator;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.RatingMPA;

import java.sql.*;
import java.time.LocalDate;

public class FilmPreparedStatementCreator implements PreparedStatementCreator {
    private final String sqlQuery;
    private final Film film;

    public FilmPreparedStatementCreator(String sqlQueryArg, Film filmArg) {
        sqlQuery = sqlQueryArg;
        film = filmArg;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        String name = film.getName();
        String description = film.getDescription();
        LocalDate releaseDate = film.getReleaseDate();
        int duration = film.getDuration();
        RatingMPA mpa = film.getMpa();

        PreparedStatement ps = con.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setString(2, description);
        ps.setDate(3, Date.valueOf(releaseDate));
        ps.setInt(4, duration);
        ps.setInt(5, mpa.getId());
        return ps;
    }
}
