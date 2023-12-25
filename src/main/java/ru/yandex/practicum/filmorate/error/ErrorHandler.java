package ru.yandex.practicum.filmorate.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.*;

import java.util.Map;

@RestControllerAdvice(basePackages = "ru.yandex.practicum.filmorate.controller")
public class ErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFoundException(final UserNotFoundException exc) {
        log.debug("Отловлена ошибка:" + exc.getMessage());
        return Map.of("Error", exc.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String, String> handleFilmNotFoundException(final FilmNotFoundException exc) {
        log.debug("Отловлена ошибка:" + exc.getMessage());
        return Map.of("Error", exc.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final ValidationException exc) {
        log.debug("Отловлена ошибка:" + exc.getMessage());
        return Map.of("Error", exc.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String, String> handleGenreNotFoundException(final GenreNotFoundException exc) {
        log.debug("Отловлена ошибка:" + exc.getMessage());
        return Map.of("Error", exc.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Map<String, String> handleRatingMPANotFoundException(final RatingMPANotFoundException exc) {
        log.debug("Отловлена ошибка:" + exc.getMessage());
        return Map.of("Error", exc.getMessage());
    }
}
