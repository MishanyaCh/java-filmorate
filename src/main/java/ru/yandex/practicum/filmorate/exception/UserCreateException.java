package ru.yandex.practicum.filmorate.exception;

public class UserCreateException extends RuntimeException {

    public UserCreateException(String message) {
        super(message);
    }
}
