package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class); // создаем логер для класса UserController
    private static final Map<Integer, User> users = new HashMap<>();
    private static final UserValidation validator = new UserValidation();
    private int id = 0;

    @GetMapping
    public List<User> getUsers() {
        log.debug("Количество пользователей в базе: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        validator.validate(user);
        if (user.getId() > 0) {
            log.debug("Пользователь '{}' c id={} не может быть добавлен.", user.getName(), user.getId() + '\n' +
                    "Для добавления нового пользователя id должен быть равен нулю");
            return null;
        }
        int userId = generateId();
        user.setId(userId);
        users.put(userId, user);
        log.debug("Пользователь '{}' c id={} успешно добавлен!", user.getName(), user.getId());
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User updatedUser) {
        validator.validate(updatedUser);
        final int id = updatedUser.getId();
        final User savedUser = users.get(id);
        if (savedUser == null) {
            log.debug("Пользователь '{}' c id={} не найден для обновления", updatedUser.getName(), updatedUser.getId());
            return null;
        }
        users.put(id, updatedUser);
        log.debug("Пользователь '{}' c id={} успешно добавлен!", updatedUser.getName(), updatedUser.getId());
        return updatedUser;
    }

    private int generateId() {
        return ++id;
    }

}
