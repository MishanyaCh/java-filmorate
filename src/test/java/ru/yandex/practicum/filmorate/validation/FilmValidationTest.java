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
                FilmValidation fv = new FilmValidation();
                fv.validate(film);
            }
        }

        executable = new MyExecutable();
    }

    @Test
    public void shouldThrowValidateExceptionWhenNameIsBlank() {
        film = new Film(" ","Описание фильма", LocalDate.of(2001, 5, 12),
                150);
        film.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("У фильма с id=" + film.getId() + " не заполнено название. Название фильма " +
                "должно быть обязательно!", exception.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionWhenDescriptionIsMoreThen200Symbols() {
        film = new Film("Фильм","Прям, ну, очень длинное описание фильма: " +
                "лплппороророророшлопоропрплрллвджаплвдаплщхуклпрхщуопхщшукорщшхоршопгуорпркацпукреке-к8пе" +
                "шорпшеоркеопршщкеорлещзорещзорзщеорзщеореорщоезорузшлпзукщлпукшлпщзплщкопщкопщкоупококщполкзщ",
                LocalDate.of(2001, 5, 12), 150);
        film.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("У фильма с id=" + film.getId() + " слишком длинное описание. " +
                "Максимальная длина описания - 200 символов.", exception.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionWhenReleaseDateIsBefore28December1895() {
        film = new Film("Фильм","Описание фильма", LocalDate.of(1700, 5, 25),
                150);
        film.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("У фильма с id=" + film.getId() + " некорректно указана дата релиза. " +
                "Дата релиза не может быть раньше чем 1895-12-28.", exception.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionWhenDurationIsNegative() {
        film = new Film("Фильм","Описание фильма", LocalDate.of(1995, 7, 18),
                -150);
        film.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("У фильма с id=" + film.getId() + " некорректно указана продолжительность. " +
                "Продолжительность фильма должна быть больше нуля!", exception.getMessage());
    }
}
