package ru.yandex.practicum.filmorate.annotation;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class MinReleaseDateValidator implements ConstraintValidator<MinReleaseDate, LocalDate> {
    private LocalDate minimumDate;

    @Override
    public void initialize(MinReleaseDate constraintAnnotation) {
        minimumDate = LocalDate.parse(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value == null || !value.isBefore(minimumDate);
    }
}