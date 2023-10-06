package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

public class UserValidation {

    public static void validate(User user) {
        String login = user.getLogin();
        String name = user.getName();

        if (login.contains(" ")) {
            throw new ValidationException("Некорректно указан логин. Логин не может содержать пробелы!");
        }
        if (name == null || name.isBlank()) {
            user.setName(login);
        }
    }
}
