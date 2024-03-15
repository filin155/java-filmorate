package ru.yandex.practicum.filmorate;

import java.time.LocalDate;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.validate.FilmValidate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmControllerTests {

    @Test
    void negativeDurationValidationExceptionTest() {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1990, 1, 1))
                .duration(-120)
                .build();

        ValidationException e = assertThrows(
                ValidationException.class,
                () -> FilmValidate.validate(film),
                "Не прошла валидация продолжительности фильма, если она отрицательная"
        );

        assertEquals("продолжительность фильма должна быть положительной", e.getMessage());
    }

    @Test
    void nameFilmBlankValidationExceptionTest() {
        Film film = Film.builder()
                .name("   ")
                .description("description")
                .releaseDate(LocalDate.of(1990, 1, 1))
                .duration(120)
                .build();

        ValidationException e = assertThrows(
                ValidationException.class,
                () -> FilmValidate.validate(film),
                "Не прошла валидация названия фильма, если оно состоит из пробелов"
        );

        assertEquals("название не может быть пустым", e.getMessage());
    }

    @Test
    void descriptionMoreThan200SymbolsValidationExceptionTest() {
        Film film = Film.builder()
                .name("name")
                .description("Very description more than 200 symbols. Very description more than 200 symbols. "
                        + "Very description more than 200 symbols. Very description more than 200 symbols. "
                        + "Very description more than 200 symbols...")
                .releaseDate(LocalDate.of(1990, 1, 1))
                .duration(120)
                .build();

        ValidationException e = assertThrows(
                ValidationException.class,
                () -> FilmValidate.validate(film),
                "Не прошла валидация описания фильма, если оно длиннее 200 символов"
        );

        assertEquals("максимальная длина описания — 200 символов", e.getMessage());
    }


    @Test
    void releaseDateBefore1895_12_28ValidationExceptionTest() {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(120)
                .build();
        ValidationException e = assertThrows(
                ValidationException.class,
                () -> FilmValidate.validate(film),
                "Не прошла валидация даты релиза фильма, если она раньше 27.12.1895"
        );

        assertEquals("дата релиза — не раньше 28 декабря 1895 года", e.getMessage());
    }

    @Test
    void nameFilmNullValidationExceptionTest() {
        Film film = Film.builder()
                .description("description")
                .releaseDate(LocalDate.of(1990, 1, 1))
                .duration(120)
                .build();

        ValidationException e = assertThrows(
                ValidationException.class,
                () -> FilmValidate.validate(film),
                "Не прошла валидация названия фильма, если названия нет"
        );

        assertEquals("название не может быть пустым", e.getMessage());
    }

}