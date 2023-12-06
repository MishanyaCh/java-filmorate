package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.RatingMPA;

import java.util.List;

public interface RatingMPAStorage {

    List<RatingMPA> getRatingsMPA();

    RatingMPA getRatingMPA(int id);
}
