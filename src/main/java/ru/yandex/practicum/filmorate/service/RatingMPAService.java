package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.RatingMPANotFoundException;
import ru.yandex.practicum.filmorate.model.RatingMPA;
import ru.yandex.practicum.filmorate.storage.RatingMPAStorage;

import java.util.List;

@Component
public class RatingMPAService {
    private final RatingMPAStorage ratingMPAStorage;

    @Autowired
    public RatingMPAService(RatingMPAStorage ratingMPAStorageArg) {
        ratingMPAStorage = ratingMPAStorageArg;
    }

    public List<RatingMPA> getRatingsMPA() {
        return ratingMPAStorage.getRatingsMPA();
    }

    public RatingMPA getRatingMPA(int id) {
        RatingMPA ratingMPA = ratingMPAStorage.getRatingMPA(id);
        if (ratingMPA == null) {
            throw new RatingMPANotFoundException(String.format("Рейтинг MPA c id=%d не найден в базе данных.", id));
        }
        return ratingMPA;
    }
}
