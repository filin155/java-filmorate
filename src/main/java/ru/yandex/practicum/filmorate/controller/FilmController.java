package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validate.FilmValidate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(value = "/films")
public class FilmController {

    //получение всех фильмов.
    Map<Integer, Film> films = new HashMap<>();
    private int id = 0;
    private int increaseId() {
        return ++id;
    }
    @GetMapping
    public Collection<Film> findAllFilms() {
        return new ArrayList<>(films.values());
    }
    @PostMapping
    public Film save(@RequestBody Film film) {
        FilmValidate.validate(film);
        film.setId(++id);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException(String.format("Фильм с id %d не найден", film.getId()));
        }
        FilmValidate.validate(film);
        films.put(film.getId(), film);
        return film;
    }




}
