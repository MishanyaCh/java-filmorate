package ru.yandex.practicum.filmorate.exception;

public class RatingMPANotFoundException extends RuntimeException {

    public RatingMPANotFoundException(final String message) {
        super(message);
    }
}
