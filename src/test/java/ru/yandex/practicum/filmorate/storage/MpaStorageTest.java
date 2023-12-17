package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.RatingMPA;
import ru.yandex.practicum.filmorate.storage.database.mpa.RatingMPADbStorage;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_= @Autowired)
public class MpaStorageTest {
    private final JdbcTemplate jdbcTemplate;
    private RatingMPAStorage mpaStorage;

    @BeforeEach
    public void beforeEach() {
        mpaStorage = new RatingMPADbStorage(jdbcTemplate);
    }

    @Test
    public void getAllRatingMpaFromDatabase() {
        List<RatingMPA> mpaList = mpaStorage.getRatingsMPA();

        Assertions.assertNotNull(mpaList);
        Assertions.assertEquals(5,mpaList.size());
    }

    @Test
    public void getRatingMpaByIdFromDatabase() {
        RatingMPA mpa = mpaStorage.getRatingMPA(4);

        assertThat(mpa)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id",4)
                .hasFieldOrPropertyWithValue("name", "R");
    }
}
