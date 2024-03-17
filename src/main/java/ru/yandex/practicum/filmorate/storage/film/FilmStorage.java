package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    public Film create(Film film);
    public Film update(Film film);
    List<Film> getAll();
    Film getById(int id);
}
