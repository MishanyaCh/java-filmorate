package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidation {
    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private static final LocalDate CINEMA_BIRTHDAY = LocalDate.of(1895,12,28);

    public void validate(Film film) {
        String name = film.getName();
        String description = film.getDescription();
        LocalDate releaseDate = film.getReleaseDate();
        int duration = film.getDuration();

        if (name.isBlank()) {
            throw new ValidationException("У фильма с id=" + film.getId() + " не заполнено название. Название фильма " +
                    "должно быть обязательно!");
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new ValidationException("У фильма с id=" + film.getId() + " слишком длинное описание. Максимальная " +
                    "длина описания - " + MAX_DESCRIPTION_LENGTH + " символов.");
        }
        if (releaseDate.isBefore(CINEMA_BIRTHDAY)) {
            throw new ValidationException("У фильма с id=" + film.getId() + " некорректно указана дата релиза. " +
                    "Дата релиза не может быть раньше чем " + CINEMA_BIRTHDAY + ".");
        }
        if (duration <= 0) {
            throw new ValidationException("У фильма с id=" + film.getId() + " некорректно указана продолжительность. " +
                    "Продолжительность фильма должна быть больше нуля!");
        }
    }
}
