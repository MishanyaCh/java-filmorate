package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
public class Film {
    @PositiveOrZero
    private int id;

    @NotBlank
    private String name;

    @Size(min = 1, max = 200)
    private String description;

    private LocalDate releaseDate;

    @Positive
    private int duration;

    private Set<Integer> likes = new HashSet<>(); // множество для хранения лайков

    public Film(String nameArg, String descriptionArg, LocalDate releaseDateArg, int durationArg) {
        name = nameArg;
        description = descriptionArg;
        releaseDate = releaseDateArg;
        duration = durationArg;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String result = "Film{" + "name='" + name + '\'' + ", id=" + id;

        if (description != null) {
            result = result + ", description.length='" + description.length() + '\'';
        } else {
            result = result + ", description.length='null'";
        }

        if (releaseDate != null) {
            result = result + ", releaseDate=" + releaseDate.format(formatter);
        } else {
            result = result + ", releaseDate='null'";
        }
        return result + ", duration=" + duration + "}" + '\n';
    }
}
