package ru.yandex.practicum.filmorate.storage.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
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
        if (user.getId() > 0) {
            log.debug("Пользователь '{}' c id={} не может быть добавлен.", user.getName(), user.getId() + '\n' +
                    "Для добавления нового пользователя id должен быть равен нулю");
            return null;
        }
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

    private int generateId() {
        return ++id;
    }
}
