package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidation {
    private final static LocalDate CURRENT_DATE = LocalDate.now();

    public void validate(User user) {
        String email = user.getEmail();
        String login = user.getLogin();
        String name = user.getName();
        LocalDate birthday = user.getBirthday();

        if (email.isBlank() || !email.contains("@")) {
            throw new ValidationException("У пользователя с id=" + user.getId() + " не заполнен или отсутствует " +
                    "e-mail адрес.");
        }
        if (login.isBlank() || login.contains(" ")) {
            throw new ValidationException("У пользователя с id=" + user.getId() + " не заполнен или некорректно " +
                    "указан логин.");
        }
        if (name.isBlank()) {
            user.setName(login);
        }
        if (birthday.isAfter(CURRENT_DATE)) {
            throw new ValidationException("У пользователя с id=" + user.getId() + " некорректно указана дата рождения.");
        }
    }

}
