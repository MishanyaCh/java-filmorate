package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserCreateException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validation.UserValidation;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired //внедряем зависимость через конструктор
    public UserService(UserStorage userStorageArg) {
        userStorage = userStorageArg;
    }

    public User createUser(User user) {
        UserValidation.validate(user);
        User createdUser = userStorage.createUser(user);
        if (createdUser == null) {
            throw new UserCreateException(String.format("Пользователь с id=%d не может быть добавлен в базу данных. " +
                    "Для добавления нового пользователя id должен быть равен нулю!", user.getId()));
        }
        return createdUser;
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
        Set<Integer> idsList = user.getFriendsIds(); // получаем поле, хранящее id друзей
        idsList.add(friendId); // добавляем нового друга к пользователю

        Set<Integer> friendIdsList = friend.getFriendsIds();
        friendIdsList.add(userId); // добавляем пользователя к другу
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
        Set<Integer> idsList = user.getFriendsIds();
        idsList.remove(friendId); // удаляем друга пользователя

        Set<Integer> friendIdsList = friend.getFriendsIds();
        friendIdsList.remove(userId); // удаляем пользователя у друга
    }

    public List<User> getUserFriends(int userId) {
        User user = userStorage.getUser(userId); // находим пользователя по id
        if (user == null) {
            throw new UserNotFoundException(String.format("Пользователь с id=%d не найден в базе данных.", userId));
        }
        List<User> usersList = new ArrayList<>(); // список друзей пользователя
        for (int id : user.getFriendsIds()) { // в цикле проходимся по id, находим друзей и добавляем в список друзей
            User friend = userStorage.getUser(id);
            usersList.add(friend);
        }
        return usersList;
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
        List<User> commonFriendsList = new ArrayList<>();
        for (int id : user.getFriendsIds()) {
            for (int otherId : otherUser.getFriendsIds()) {
                if (id == otherId) {
                    User commonFriend = userStorage.getUser(id);
                    commonFriendsList.add(commonFriend);
                }
            }
        }
        return commonFriendsList;
    }
}
