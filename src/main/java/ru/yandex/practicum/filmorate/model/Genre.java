package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@EqualsAndHashCode
public class Genre {
    @PositiveOrZero
    private int id;

    @NotBlank
    private String name;

    public Genre(String nameArg) {
        name = nameArg;
    }

    @Override
    public String toString() {
        return "Genre{" + "name='" + name + '\'' + ", id=" + id + "}";
    }
}
