package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class); // создаем логер для класса UserController
    private final UserService userService;

    @Autowired
    public UserController (UserService userServiceArg) {
        userService = userServiceArg;
    }

    @GetMapping
    public List<User> getUsers() {
        log.debug("Пришел GET /users запрос");
        final List<User> usersList = userService.getUsers();
        log.debug("На запрос GET /users отправлен ответ с размером тела: {}", usersList.size());
        return usersList;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        log.debug("Пришел GET /users/{} запрос", id);
        final User user = userService.getUser(id);
        log.debug("На запрос GET /users/{} отправлен ответ с размером тела: 1", id);
        return user;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        log.debug("Пришел POST /users запрос с телом: {}", user);
        final User createdUser = userService.createUser(user);
        log.debug("На запрос POST /users отправлен ответ c телом: {}", createdUser);
        return createdUser;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.debug("Пришел PUT /users запрос с телом: {}", user);
        final User updatedUser = userService.updateUser(user);
        log.debug("На запрос PUT /users отправлен ответ c телом: {}", updatedUser);
        return updatedUser;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Пришел PUT /users/{}/friends/{} запрос", id, friendId);
        userService.addFriend(id, friendId);
        log.debug("Пользователь с id={} добавил в друзья пользователя с id={}", id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Пришел DELETE /users/{}/friends/{} запрос", id, friendId);
        userService.deleteFriend(id, friendId);
        log.debug("Пользователь с id={} удалил из друзей пользователя с id={}", id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable int id) {
        log.debug("Пришел GET /users/{}/friends запрос", id);
        final List<User> userFriendsList = userService.getUserFriends(id);
        log.debug("На запрос GET /users/{}/friends отправлен ответ с размером тела: {}", id, userFriendsList.size());
        return userFriendsList;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.debug("Пришел GET /users/{}/friends/common/{} запрос", id, otherId);
        final List<User> commonFriendsList = userService.getCommonFriendsWithOtherUser(id, otherId);
        log.debug("На запрос GET /users/{}/friends/common/{otherId} отправлен ответ с размером тела: {}", id,
                commonFriendsList.size());
        return commonFriendsList;
    }
}
