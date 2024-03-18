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

import org.springframework.web.bind.annotation.DeleteMapping;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}/friends")
    public ArrayList<User> getFriends(@PathVariable int id) {
        log.debug("Ищем список друзей пользователя с id: {}", id);
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ArrayList<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.debug("Ищем общих друзей пользователей: {}, {}", id, otherId);
        return userService.getCommonFriends(id, otherId);
    }

    @GetMapping()
    public List<User> getAll() {
        log.debug("Запрашиваем список пользователей");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        log.debug("Запрашиваем пользователя с id: {}", id);
        return userService.getById(id);
    }

    @PostMapping()
    public User create(@RequestBody @Valid User user) {
        log.debug("Добавляем пользователя: {}", user);
        return userService.create(user);
    }

    @PutMapping()
    public User update(@RequestBody @Valid User user) {
        log.debug("Обновляем пользователя: {}", user);
        return userService.update(user);
    }


    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Добавляем пользователя с id {}, в друзья к пользователю с id {}", friendId, id);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.debug("Удаляем пользователя по id: {}", id);
        userService.deleteFriend(id, friendId);
    }

}