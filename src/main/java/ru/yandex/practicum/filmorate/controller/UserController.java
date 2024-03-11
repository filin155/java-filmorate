package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validate.UserValidate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    Map<Integer, User> users = new HashMap<>();
    private int id = 0;
    private int increaseId() {
        return ++id;
    }
    @GetMapping
    public Collection<User> findAllFilms() {
        return new ArrayList<>(users.values());
    }
    @PostMapping
    public User create(@RequestBody User user) {
        UserValidate.validate(user);
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException(String.format("Пользователь с id %d не найден", user.getId()));
        }
        UserValidate.validate(user);
        users.put(user.getId(), user);
        return user;
    }



}
