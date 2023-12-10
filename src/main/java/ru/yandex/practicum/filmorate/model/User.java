package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
public class User {
    @PositiveOrZero
    private int id;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private String login;

    private String name;

    @PastOrPresent
    private LocalDate birthday;

    @JsonIgnore
    private Set<Integer> friendsIds = new HashSet<>(); // множество для хранения id добавляемых друзей

    public User() {

    }

    public User(String emailArg, String loginArg, String nameArg, LocalDate birthdayArg) {
        email = emailArg;
        login = loginArg;
        name = nameArg;
        birthday = birthdayArg;
    }

    public User(int idArg, String emailArg, String loginArg, String nameArg, LocalDate birthdayArg) {
        id = idArg;
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
