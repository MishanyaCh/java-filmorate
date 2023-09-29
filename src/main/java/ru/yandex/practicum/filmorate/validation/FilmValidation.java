package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidation {
    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895,12,28);

    public static void validate(Film film) {
        LocalDate releaseDate = film.getReleaseDate();

        if (releaseDate.isBefore(CINEMA_BIRTHDAY)) {
            throw new ValidationException("Некорректно указана дата релиза. Дата релиза не может быть " +
                    "раньше чем " + CINEMA_BIRTHDAY + ".");
        }
    }
}
