package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User createUser(User user);

    User updateUser(User user);

    List<User> getUsers();

    User getUser(int id);

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);
}
