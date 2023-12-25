package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired //внедряем зависимость через конструктор
    public UserService(@Qualifier("UserDbStorage") UserStorage userStorageArg) {
        userStorage = userStorageArg;
    }

    public User createUser(User user) {
        UserValidation.validate(user);
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        UserValidation.validate(user);
        User updatedUser = userStorage.updateUser(user);
        if (updatedUser == null) {
            throw new UserNotFoundException(String.format("Пользователь с id=%d отсутствует в базе данных. " +
                    "Для обновления данных о пользователе сперва добавте пользователя!", user.getId()));
        }
        return updatedUser;
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUser(int userId) {
        User user = userStorage.getUser(userId);
        if (user == null) {
            throw new UserNotFoundException(String.format("Пользователь с id=%d не найден в базе данных.", userId));
        }
        return user;
    }

    public void addFriend(int userId, int friendId) {
        User user = userStorage.getUser(userId); // находим пользователя по id
        User friend = userStorage.getUser(friendId); // находим друга по id
        if (user == null) {
            throw new UserNotFoundException(String.format("Пользователь с id=%d не найден в базе данных.", userId));
        }
        if (friend == null) {
            throw new UserNotFoundException(String.format("Пользователь с id=%d не найден в базе данных. ", friendId));
        }
        userStorage.addFriend(userId, friendId); // добавляем нового друга к пользователю
    }

    public void deleteFriend(int userId, int friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        if (user == null) {
            throw new UserNotFoundException(String.format("Пользователь с id=%d не найден в базе данных. ", userId));
        }
        if (friend == null) {
            throw new UserNotFoundException(String.format("Пользователь с id=%d не найден в базе данных.", friendId));
        }
        userStorage.deleteFriend(userId, friendId); // удаляем друга пользователя
    }

    public List<User> getUserFriends(int userId) {
        User user = userStorage.getUser(userId);
        if (user == null) {
            throw new UserNotFoundException(String.format("Пользователь с id=%d не найден в базе данных.", userId));
        }
        return userStorage.getUserFriends(userId);
    }

    public List<User> getCommonFriendsWithOtherUser(int userId, int otherUserId) {
        User user = userStorage.getUser(userId);
        User otherUser = userStorage.getUser(otherUserId);
        if (user == null) {
            throw new UserNotFoundException(String.format("Пользователь с id=%d не найден в базе данных", userId));
        }
        if (otherUser == null) {
            throw new UserNotFoundException(String.format("Пользователь с id=%d не найден в базе данных", otherUserId));
        }
        return userStorage.getCommonFriendsWithOtherUser(userId, otherUserId);
    }
}
