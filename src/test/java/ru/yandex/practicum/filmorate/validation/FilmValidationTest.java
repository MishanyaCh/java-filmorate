package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmValidationTest {
    private Film film;
    private Executable executable;

    @BeforeEach
    public void beforeEach() {
        class MyExecutable implements Executable {

            @Override
            public void execute() {
                FilmValidation.validate(film);
            }
        }

        executable = new MyExecutable();
    }

    @Test
    public void shouldThrowValidateExceptionWhenReleaseDateIsBefore28December1895() {
        film = new Film("Фильм","Описание фильма", LocalDate.of(1700, 5, 25),
                150);
        film.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("Некорректно указана дата релиза. " +
                "Дата релиза не может быть раньше чем 1895-12-28.", exception.getMessage());
    }
}
