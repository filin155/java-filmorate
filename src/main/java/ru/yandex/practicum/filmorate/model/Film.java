package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;


/**
 * Film.
 */

@Data
@Builder
public class Film {
//
//    private int id;
//    private String name;
//    private String description;
//    private Date releaseDate;
//    private int duration;

    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;


//    целочисленный идентификатор — id;
//    название — name;
//    описание — description;
//    дата релиза — releaseDate;
//    продолжительность фильма — duration.

}
