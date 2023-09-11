package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@EqualsAndHashCode
public class User {
    private int id;
    private final String email;
    private final String login;
    private String name;
    private final LocalDate birthday;

    public User(String emailArg, String loginArg, String nameArg, LocalDate birthdayArg) {
        email = emailArg;
        login = loginArg;
        name = nameArg;
        birthday = birthdayArg;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String result = "User{" + "login='" + login + '\'' + ", id=" + id + ", name='" + name + '\'' +
                ", e-mail='" + email + '\'';

        if (birthday != null) {
            result = result + ", birthday=" + birthday.format(formatter);
        } else {
            result = result + ", birthday='null'";
        }
        return result + "}" + '\n';
    }
}
