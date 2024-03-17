package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.MinReleaseDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
public class Film {
    private int id;
    @NotNull
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @MinReleaseDate
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private final Set<Integer> usersLiked = new HashSet<>();

    public void addLike(int userId) {
        usersLiked.add(userId);
    }
    public void deleteLike(int userId) {
        usersLiked.remove(userId);
    }
    public int getLikes() {
        return usersLiked.size();
    }
}