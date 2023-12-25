package ru.yandex.practicum.filmorate.storage.database.film;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.RatingMPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FilmRowMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        // получаем значение из колонки "id" таблицы films
        int id = rs.getInt(1);
        // получаем значение из колонки "name" таблицы films
        String name = rs.getString(2);
        // получаем значение из колонки "description" таблицы films
        String description = rs.getString(3);
        // получаем значение из колонки "releaseDate" таблицы films
        LocalDate releaseDate = rs.getDate(4).toLocalDate();
        // получаем значение из колонки "duration" таблицы films
        int duration = rs.getInt(5);
        // получаем значение из колонки "ratingMPA_id" таблицы films
        int mpaId = rs.getInt(6);
        // получаем значение из колонки "name" таблицы rating_MPA
        String mpaName = rs.getString(7);
        RatingMPA mpa = new RatingMPA(mpaId, mpaName);

        return new Film(id, name, description, releaseDate, duration, mpa);
    }
}
