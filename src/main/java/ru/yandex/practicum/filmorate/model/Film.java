package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@EqualsAndHashCode
public class Film {
    private int id;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;

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
