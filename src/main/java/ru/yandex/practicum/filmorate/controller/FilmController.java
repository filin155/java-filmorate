package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import javax.validation.Valid;

import java.util.List;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;


@Slf4j
@RestController
@RequestMapping(value = "/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping()
    public List<Film> getAll() {
        log.debug("Получили список всех фильмов.");
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable int id) {
        log.debug("Получили фильм по id {}.", id);
        return filmService.getById(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(defaultValue = "10", required = false) int count) {
        log.debug("Получили {} популярных фильмов.", count);
        return filmService.getPopular(count);
    }

    @PostMapping()
    public Film create(@RequestBody @Valid Film film) {
        log.debug("Создаем фильм {}.", film);
        return filmService.create(film);
    }

    @PutMapping()
    public Film update(@RequestBody @Valid Film film) {
        log.debug("Обновляем фильм {}.", film);
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.debug("Пользователь с id {} ставит лайк фильму с id {}", userId, id);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.debug("Пользователь с id {} удаляет лайк к фильму с id {}", userId, id);
        filmService.deleteLike(id, userId);
    }

}