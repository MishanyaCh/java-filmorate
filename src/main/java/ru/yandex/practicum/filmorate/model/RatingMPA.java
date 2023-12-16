package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@EqualsAndHashCode
public class RatingMPA {
    @Positive
    private int id;

    @NotBlank
    private String name;

    public RatingMPA() {}

    public RatingMPA(int idArg, String nameArg) {
        id = idArg;
        name = nameArg;
    }

    @Override
    public String toString() {
        return "RatingMPA{" + "name='" + name + '\'' + ", id=" + id + "}";
    }
}
