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
    public void shouldThrowValidateExceptionWhenEmailIsEmpty() {
        user = new User(null, "Esperanto","Миша",
                LocalDate.of(1992,11,15));
        user.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("Отсутствует или некорректно указан e-mail адрес.", exception.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionWhenEmailDoesNotContain_Email_Symbol() {
        user = new User("chizh909mail.ru", "Esperanto","Миша",
                LocalDate.of(1992,11,15));
        user.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("Отсутствует или некорректно указан e-mail адрес.", exception.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionWhenLoginIsEmpty() {
        user = new User("chizh909@mail.ru", null,"Миша",
                LocalDate.of(1992,11,15));
        user.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("Отсутствует или некорректно указан логин.",
                exception.getMessage());
    }

    @Test
    public void shouldThrowValidateExceptionWhenLoginContainsSpaces() {
        user = new User("chizh909@mail.ru", " Esperanto ","Миша",
                LocalDate.of(1992,11,15));
        user.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("Отсутствует или некорректно указан логин.",
                exception.getMessage());
    }

    @Test
    public void shouldSetLoginInFieldNameWhenNameIsEmpty() {
        user = new User("chizh909@mail.ru", "Esperanto",null,
                LocalDate.of(1992,11,15));
        user.setId(1);
        user.setName("Esperanto");
        assertEquals("Esperanto", user.getLogin());
    }

    @Test
    public void shouldThrowValidateExceptionWhenBirthdayIsAfterCurrentDay() {
        user = new User("chizh909@mail.ru", "Esperanto","Миша",
                LocalDate.of(2333,11,15));
        user.setId(1);
        final ValidationException exception = assertThrows(ValidationException.class, executable);
        assertEquals("Некорректно указана дата рождения.", exception.getMessage());
    }

}
