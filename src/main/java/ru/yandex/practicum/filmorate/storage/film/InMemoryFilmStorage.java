package ru.yandex.practicum.filmorate.storage.film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @Override
    public Film create(Film film) {
        film.setId(increaseId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        int filmId = film.getId();
        if (!films.containsKey(filmId)) {
            throw new ValidationException(String.format("Фильм с id %d не найден", filmId));
        }
        films.put(filmId, film);
        return film;
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getById(int id) {
        if (!films.containsKey(id)) {
            throw new ValidationException(String.format("Фильм с id %d не найден", id));
        }
        return films.get(id);
    }

    private int increaseId() {
        return ++id;
    }
}