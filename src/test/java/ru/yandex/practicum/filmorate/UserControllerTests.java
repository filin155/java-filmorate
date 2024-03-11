package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validate.UserValidate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserControllerTests {

    @Test
    void emailNullValidationExceptionTest() {
        User user = User.builder()
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();


        ValidationException e = assertThrows(
                ValidationException.class,
                () -> UserValidate.validate(user),
                "Не прошла валидация емейла, если его нет"
        );

        assertEquals("электронная почта не может быть пустой и должна содержать символ @", e.getMessage());
    }


    @Test
    void emailBlankValidationExceptionTest() {
        User user = User.builder()
                .email("  ")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        ValidationException e = assertThrows(
                ValidationException.class,
                () -> UserValidate.validate(user),
                "Не прошла валидация емейла, если он состоит из пробелов"
        );

        assertEquals("электронная почта не может быть пустой и должна содержать символ @", e.getMessage());
    }

    @Test
    void loginNullValidationExceptionTest() {
        User user = User.builder()
                .email("mail@mail.ru")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        ValidationException e = assertThrows(
                ValidationException.class,
                () -> UserValidate.validate(user),
                "Не прошла валидация логина, если его нет"
        );

        assertEquals("логин не может быть пустым и содержать пробелы", e.getMessage());
    }

    @Test
    void loginBlankValidationExceptionTest() {
        User user = User.builder()
                .login("")
                .email("mail@mail.ru")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        ValidationException e = assertThrows(
                ValidationException.class,
                () -> UserValidate.validate(user),
                "Не прошла валидация логина, если он состоит из пустой строки"
        );

        assertEquals("логин не может быть пустым и содержать пробелы", e.getMessage());
    }

    @Test
    void birthdayInFutureValidationExceptionTest() {
        User user = User.builder()
                .login("login")
                .email("mail@mail.ru")
                .name("name")
                .birthday(LocalDate.of(2446, 8, 20))
                .build();

        ValidationException e = assertThrows(
                ValidationException.class,
                () -> UserValidate.validate(user),
                "Не прошла валидация дня рождения, если он в будущем"
        );

        assertEquals("дата рождения не может быть в будущем", e.getMessage());
    }

    @Test
    void loginContainsSpaceValidationExceptionTest() {
        User user = User.builder()
                .login("login with space")
                .email("mail@mail.ru")
                .name("name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        ValidationException e = assertThrows(
                ValidationException.class,
                () -> UserValidate.validate(user),
                "Не прошла валидация логина, если он содержит пробелы"
        );

        assertEquals("логин не может быть пустым и содержать пробелы", e.getMessage());
    }

    @Test
    void nameNullUseLoginTest() {
        User user = User.builder()
                .login("login")
                .email("mail@mail.ru")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        UserValidate.validate(user);
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


        ValidationException e = assertThrows(
                ValidationException.class,
                () -> UserValidate.validate(user),
                "Не прошла валидация емейла, если он не содержит @"
        );

        assertEquals("электронная почта не может быть пустой и должна содержать символ @", e.getMessage());
    }

}
