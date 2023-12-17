package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.database.genre.GenreDbStorage;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreDbStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private GenreStorage genreStorage;

    @BeforeEach
    public void beforeEach() {
        genreStorage = new GenreDbStorage(jdbcTemplate);
    }

    @Test
    public void getAllGenreFromDatabase() {
        List<Genre> genresList = genreStorage.getGenres();

        Assertions.assertNotNull(genresList);
        Assertions.assertEquals(6, genresList.size());
    }

    @Test
    public void getGenreByIdFromDatabase() {
        Genre genre = genreStorage.getGenre(3);

        assertThat(genre)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id",3)
                .hasFieldOrPropertyWithValue("name", "Мультфильм");


    }
}
