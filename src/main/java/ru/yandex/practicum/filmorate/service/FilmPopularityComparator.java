package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

public class FilmPopularityComparator implements Comparator<Film> {

    @Override
    public int compare(Film film1, Film film2) {
        int film1_QuantityLikes = film1.getLikes().size();
        int film2_QuantityLikes = film2.getLikes().size();
        return film1_QuantityLikes - film2_QuantityLikes;
    }
}
