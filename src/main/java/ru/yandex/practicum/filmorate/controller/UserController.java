package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class); // создаем логер для класса UserController
    private Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @GetMapping
    public List<User> getUsers() {
        log.debug("Пришел GET /users запрос");
        final List<User> usersList = new ArrayList<>(users.values());
        log.debug("На запрос GET /users отправлен ответ с размером тела: {}", usersList.size());
        return usersList;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        UserValidation.validate(user);
        log.debug("Пришел POST /users запрос с телом: {}", user);
        if (user.getId() > 0) {
            log.debug("Пользователь '{}' c id={} не может быть добавлен.", user.getName(), user.getId() + '\n' +
                    "Для добавления нового пользователя id должен быть равен нулю");
            return null;
        }
        int userId = generateId();
        user.setId(userId);
        users.put(userId, user);
        log.debug("На запрос POST /users отправлен ответ c телом: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User updatedUser) {
        UserValidation.validate(updatedUser);
        log.debug("Пришел PUT /users запрос с телом: {}", updatedUser);
        final int id = updatedUser.getId();
        final User savedUser = users.get(id);
        if (savedUser == null) {
            log.debug("Пользователь '{}' c id={} не найден для обновления", updatedUser.getName(), updatedUser.getId());
            throw new ValidationException("Пользователь c id=" + id + " не найден для обновления");
        }
        users.put(id, updatedUser);
        log.debug("На запрос PUT /users отправлен ответ c телом: {}", updatedUser);
        return updatedUser;
    }

    private int generateId() {
        return ++id;
    }
}
