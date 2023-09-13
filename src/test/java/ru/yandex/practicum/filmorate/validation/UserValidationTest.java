package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserValidationTest {
    private User user;
    private Executable executable;

    @BeforeEach
    public void beforeEach() {
        class MyExecutable implements Executable {

            @Override
            public void execute() {
                UserValidation uv = new UserValidation();
                uv.validate(user);
            }
        }

        executable = new MyExecutable();
    }

    @Test
    public void shouldThrowValidateExceptionWhenEmailIsBlank() {
        user = new User(" ", "Esperanto","Миша",
                LocalDate.of(1992,11,15));
        user.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("У пользователя с id=" + user.getId() + " не заполнен или отсутствует e-mail адрес.",
                exception.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionWhenEmailDoesNotContain_Email_Symbol() {
        user = new User("chizh909mail.ru", "Esperanto","Миша",
                LocalDate.of(1992,11,15));
        user.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("У пользователя с id=" + user.getId() + " не заполнен или отсутствует e-mail адрес.",
                exception.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionWhenLoginIsBlank() {
        user = new User("chizh909@mail.ru", " ","Миша",
                LocalDate.of(1992,11,15));
        user.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("У пользователя с id=" + user.getId() + " не заполнен или некорректно указан логин.",
                exception.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionWhenLoginContainsSpaces() {
        user = new User("chizh909@mail.ru", " Esperanto ","Миша",
                LocalDate.of(1992,11,15));
        user.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("У пользователя с id=" + user.getId() + " не заполнен или некорректно указан логин.",
                exception.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionWhenBirthdayIsAfterCurrentDay() {
        user = new User("chizh909@mail.ru", "Esperanto","Миша",
                LocalDate.of(2333,11,15));
        user.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("У пользователя с id=" + user.getId() + " некорректно указана дата рождения.",
                exception.getMessage());
    }

}
