package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal, int authUserId);

    // false if not found
    boolean delete(int id, int authUserId);

    // null if not found
    Meal get(int id, int authUserId);

    //ordered dateTime desc
    List<Meal> getAll(int authUserId);

    List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int authUserId);
}
