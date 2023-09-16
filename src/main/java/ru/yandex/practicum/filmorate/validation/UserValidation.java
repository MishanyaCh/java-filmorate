package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserValidation {
    private static final LocalDate CURRENT_DATE = LocalDate.now();

    public void validate(User user) {
        String email = user.getEmail();
        String login = user.getLogin();
        String name = user.getName();
        LocalDate birthday = user.getBirthday();

        if (email == null || !email.contains("@")) {
            throw new ValidationException("Отсутствует или некорректно указан e-mail адрес.");
        }
        if (login == null || login.contains(" ")) {
            throw new ValidationException("Отсутствует или некорректно указан логин.");
        }
        if (name == null) {
            user.setName(login);
        }
        if (birthday.isAfter(CURRENT_DATE)) {
            throw new ValidationException("Некорректно указана дата рождения.");
        }
    }

}
