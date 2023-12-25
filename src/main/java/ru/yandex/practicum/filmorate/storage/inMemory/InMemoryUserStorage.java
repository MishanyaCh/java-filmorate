package ru.yandex.practicum.filmorate.storage.inMemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Qualifier("InMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserStorage.class); // создаем логер
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User createUser(User user) {
        int userId = generateId();
        user.setId(userId);
        users.put(userId, user);
        return user;
    }

    @Override
    public User updateUser(User updatedUser) {
        final int id = updatedUser.getId();
        final User savedUser = users.get(id);
        if (savedUser == null) {
            log.debug("Пользователь '{}' c id={} не найден для обновления", updatedUser.getName(), updatedUser.getId());
            return null;
        }
        users.put(id, updatedUser);
        return updatedUser;
    }

    @Override
    public User getUser(int id) {
        final User user = users.get(id);
        if (user == null) {
            log.debug("пользователь с id={} не найден в базе данных", id);
            return null;
        }
        return user;
    }

    @Override
    public void addFriend(int userId, int friendId) {
        User user = users.get(userId);
        Set<Integer> idsList = user.getFriendsIds(); // получаем поле, хранящее id друзей
        idsList.add(friendId); // добавляем нового друга к пользователю

        User friend = users.get(friendId);
        Set<Integer> friendIdsList = friend.getFriendsIds();
        friendIdsList.add(userId); // добавляем пользователя к другу
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        User user = users.get(userId);
        Set<Integer> idsList = user.getFriendsIds(); // получаем поле, хранящее id друзей
        idsList.remove(friendId); // удаляем друга пользователя

        User friend = users.get(friendId);
        Set<Integer> friendIdsList = friend.getFriendsIds();
        friendIdsList.remove(userId); // удаляем пользователя у друга
    }

    @Override
    public List<User> getUserFriends(int userId) {
        List<User> friendsList = new ArrayList<>(); // список друзей пользователя
        User user = users.get(userId); // находим пользователя по id
        for (int id : user.getFriendsIds()) { // в цикле проходимся по id, находим друзей и добавляем в список друзей
            User friend = users.get(id);
            friendsList.add(friend);
        }
        return friendsList;
    }

    @Override
    public List<User> getCommonFriendsWithOtherUser(int userId, int otherUserId) {
        List<User> commonFriendsList = new ArrayList<>();
        User user = users.get(userId);
        User otherUser = users.get(otherUserId);
        for (int id : user.getFriendsIds()) {
            for (int otherId : otherUser.getFriendsIds()) {
                if (id == otherId) {
                    User commonFriend = users.get(id);
                    commonFriendsList.add(commonFriend);
                }
            }
        }
        return commonFriendsList;
    }

    private int generateId() {
        return ++id;
    }
}
