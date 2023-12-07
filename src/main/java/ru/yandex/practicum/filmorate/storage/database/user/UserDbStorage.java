package ru.yandex.practicum.filmorate.storage.database.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class UserDbStorage implements UserStorage {
    private static final Logger log = LoggerFactory.getLogger(UserDbStorage.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplateArg) {
        jdbcTemplate = jdbcTemplateArg;
    }

    public User createUser(User user) {
        String sqlQuery = "INSERT INTO users (name, login, email, birthday) " +
                "VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new UserPreparedStatementCreator(user, sqlQuery), keyHolder);
        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        user.setId(id);
        return user;
    }

    public User updateUser(User user) {
        String sqlQuery = "UPDATE users SET name = ?, login = ?, email = ?, birthday = ? " +
                "WHERE id = ?";
        String name = user.getName();
        String login = user.getLogin();
        String email = user.getEmail();
        LocalDate birthday = user.getBirthday();
        int id = user.getId();
        Object[] args = {name, login, email, birthday, id};
        jdbcTemplate.update(sqlQuery, args);
        return user;
    }

    public List<User> getUsers() {
        String sqlQuery = "SELECT * FROM users";
        return jdbcTemplate.query(sqlQuery, new UserRowMapper());
    }

    public User getUser(int id) {
        String sqlQuery = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sqlQuery, new UserRowMapper(), id);
        if (users.isEmpty()) {
            log.debug("Пользователь с id={} не найден в базе данных", id);
            return null;
        }
        return users.get(0);
    }
}
