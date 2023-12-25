package ru.yandex.practicum.filmorate.storage.database.user;

import org.springframework.jdbc.core.PreparedStatementCreator;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.*;
import java.time.LocalDate;

public class UserPreparedStatementCreator implements PreparedStatementCreator {
    private final User user;
    private final String sqlQuery;

    public UserPreparedStatementCreator(User userArg, String sqlQueryArg) {
        user = userArg;
        sqlQuery = sqlQueryArg;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        String name = user.getName();
        String login = user.getLogin();
        String email = user.getEmail();
        LocalDate birthday = user.getBirthday();

        PreparedStatement ps = con.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setString(2, login);
        ps.setString(3, email);
        ps.setDate(4, Date.valueOf(birthday));
        return ps;
    }
}
