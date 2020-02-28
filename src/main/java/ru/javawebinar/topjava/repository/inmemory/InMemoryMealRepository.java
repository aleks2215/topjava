package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
//        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        for (Meal meal: MealsUtil.MEALS) {
            save(meal, 1);
        }
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

    @Override
    public Collection<Meal> getAll(int authUserId) {
        return repository.values().stream()
                .filter(meal -> {
                    System.out.println(meal.getUserId() + " "+ authUserId);
                    return meal.getUserId().equals(authUserId);
                })
                .collect(Collectors.toList());
    }

    private boolean wrongUserId(int mealId, int authUserId) {
        if (repository.get(mealId) == null) {
            return true;
        }
        return !repository.get(mealId).getUserId().equals(authUserId);
    }

    @Override
    public Collection<Meal> filterByDate(int authUserId) {
        return getAll(authUserId);
    }
}

