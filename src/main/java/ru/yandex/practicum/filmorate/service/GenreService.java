package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Service
public class GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorageArg) {
        genreStorage = genreStorageArg;
    }

    public List<Genre> getGenres() {
        return genreStorage.getGenres();
    }

    public Genre getGenre(int id) {
        Genre genre = genreStorage.getGenre(id);
        if (genre == null) {
            throw new GenreNotFoundException(String.format("Жанр с id=%d не найден в базе данных.", id));
        }
        return genre;
    }
}
