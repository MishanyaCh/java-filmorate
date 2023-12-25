package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.RatingMPA;
import ru.yandex.practicum.filmorate.storage.database.film.FilmDbStorage;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private FilmStorage filmStorage;
    private Film film1;
    private Film film2;

    @BeforeEach
    public void beforeEach() {
        filmStorage = new FilmDbStorage(jdbcTemplate);
        LinkedHashSet<Genre> genres = new LinkedHashSet<>();

        Genre genre1 = new Genre(2, "Драма");
        genres.add(genre1);

        RatingMPA mpaForFilm1 = new RatingMPA(3,"PG-13");
        film1 = new Film("Фильм 1", "Описание для фильма 1",
                LocalDate.of(1999, 5, 16), 120);
        film1.setMpa(mpaForFilm1);

        RatingMPA mpaForFilm2 = new RatingMPA(4, "R");
        film2 = new Film("Фильм 2", "Описание для фильма 2",
                LocalDate.of(2003,11,18), 90);
        film2.setMpa(mpaForFilm2);
        film2.setGenres(genres);
    }

    @Test
    public void createNewFilm() {
        Film createdFilmWithoutGenre = filmStorage.createFilm(film1);
        int filmId = createdFilmWithoutGenre.getId();
        Film createdFilmWithGenre = filmStorage.createFilm(film2);
        int otherFilmId = createdFilmWithGenre.getId();

        assertThat(createdFilmWithoutGenre)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", filmId)
                .usingRecursiveComparison().isEqualTo(film1);

        assertThat(createdFilmWithGenre)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id",otherFilmId)
                .usingRecursiveComparison().isEqualTo(film2);
    }

    @Test
    public void updateExistingFilmWithoutGenre() {
        Film film = filmStorage.createFilm(film1);
        int filmId = film.getId();

        RatingMPA mpa = new RatingMPA(3, "PG-13");
        Film updatedFilm1 = new Film(filmId, "Фильм 1", "Описание для фильма 1",
                LocalDate.of(1999, 5, 16), 120, mpa);

        LinkedHashSet<Genre> genres = new LinkedHashSet<>();
        for (int i = 1; i <= 3; i++) {
            Genre genre = new Genre(i,null);
            genres.add(genre);
        }
        updatedFilm1.setGenres(genres);

        Film updatedFilmWithGenres = filmStorage.updateFilm(updatedFilm1);

        assertThat(updatedFilmWithGenres)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(updatedFilm1);
    }

    @Test
    public void getAllFilms() {
        filmStorage.createFilm(film1);
        filmStorage.createFilm(film2);
        List<Film> films = filmStorage.getFilms();

        Assertions.assertNotNull(films);
        Assertions.assertEquals(2, films.size());
    }

    @Test
    public void returnEmptyListWhenFilmNotCreated() {
        List<Film> films = filmStorage.getFilms();

        Assertions.assertNotNull(films);
        Assertions.assertTrue(films.isEmpty());
    }

    @Test
    public void getFilmById() {
        filmStorage.createFilm(film1);
        Film createdFilm2 = filmStorage.createFilm(film2);
        int filmId = createdFilm2.getId();
        Film film = filmStorage.getFilm(filmId);

        assertThat(film)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(createdFilm2);
    }

    @Test
    public void returnNullWhenNotFindFilmById() {
        filmStorage.createFilm(film1);
        Film film = filmStorage.getFilm(2);

        Assertions.assertNull(film);
    }
}
