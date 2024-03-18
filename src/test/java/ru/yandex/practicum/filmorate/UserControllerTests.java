package ru.yandex.practicum.filmorate;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class UserControllerTests {
    UserController userController;

    private static Validator validator;

    @Autowired
    public UserControllerTests(UserController userController) {
        this.userController = userController;

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    void emailNullValidationExceptionTest() {
        User user = User.builder()
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
                violations.isEmpty(),
                "Не прошла валидация емейла, если его нет"
        );
    }


    @Test
    void emailBlankValidationExceptionTest() {
        User user = User.builder()
                .email("  ")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
                violations.isEmpty(),
                "Не прошла валидация емейла, если он состоит из пробелов"
        );
    }

    @Test
    void loginNullValidationExceptionTest() {
        User user = User.builder()
                .email("mail@mail.ru")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
                violations.isEmpty(),
                "Не прошла валидация логина, если его нет"
        );
    }

    @Test
    void loginBlankValidationExceptionTest() {
        User user = User.builder()
                .login("")
                .email("mail@mail.ru")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
                violations.isEmpty(),
                "Не прошла валидация логина, если он состоит из пустой строки"
        );

    }

    @Test
    void birthdayInFutureValidationExceptionTest() {
        User user = User.builder()
                .login("login")
                .email("mail@mail.ru")
                .name("name")
                .birthday(LocalDate.of(2446, 8, 20))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
                violations.isEmpty(),
                "Не прошла валидация дня рождения, если он в будущем"
        );

    }

    @Test
    void loginContainsSpaceValidationExceptionTest() {
        User user = User.builder()
                .login("login with space")
                .email("mail@mail.ru")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();


        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
                violations.isEmpty(),
                "Не прошла валидация логина, если он содержит пробелы"
        );
    }

    @Test
    void nameNullUseLoginTest() {
        User user = User.builder()
                .login("login")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        userController.create(user);
        assertEquals(user.getLogin(), user.getName(), "Не подставился логин в поле имя, если оно было пустым");
    }

    @Test
    void emailNoAtValidationExceptionTest() {
        User user = User.builder()
                .email("mail.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();


        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(
                violations.isEmpty(),
                "Не прошла валидация емейла, если он не содержит @"
        );
    }

}
