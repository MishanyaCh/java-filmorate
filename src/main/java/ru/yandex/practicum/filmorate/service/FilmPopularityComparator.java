package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

public class FilmPopularityComparator implements Comparator<Film> {

    @Override
    public int compare(Film film1, Film film2) {
        int quantityLikesByFilm1 = film1.getLikes().size();
        int quantityLikesByFilm2 = film2.getLikes().size();
        return quantityLikesByFilm1 - quantityLikesByFilm2;
    }
}
