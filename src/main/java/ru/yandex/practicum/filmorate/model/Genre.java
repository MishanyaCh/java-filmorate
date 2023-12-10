package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@EqualsAndHashCode
public class Genre {
    @Positive
    private int id;

    @NotBlank
    private String name;

    public Genre() {

    }

    public Genre(int idArd, String nameArg) {
        id = idArd;
        name = nameArg;
    }

    @Override
    public String toString() {
        return "Genre{" + "name='" + name + '\'' + ", id=" + id + "}";
    }
}
