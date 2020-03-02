package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int authUserId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(authUserId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int authUserId) {
        if (wrongUserId(id, authUserId)) {
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int authUserId) {
        if (wrongUserId(id, authUserId)) {
            return null;
        }
        return repository.get(id);
    }

    private boolean wrongUserId(int mealId, int authUserId) {
        if (repository.get(mealId) == null) {
            return true;
        }
        return !repository.get(mealId).getUserId().equals(authUserId);
    }

    @Override
    public List<Meal> getAll(int authUserId) {
        return getAllFiltered(authUserId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int authUserId) {
        return getAllFiltered(authUserId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDate, endDate));
    }

    public List<Meal> getAllFiltered(int authUserId, Predicate<Meal> filter) {
        List<Meal> meals = repository.values().stream()
                .filter(meal -> meal.getUserId().equals(authUserId))
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() : meals;
    }
}

