package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int authUserId) {
        return repository.save(meal, authUserId);
//        return null;
    }

    public void delete(int id, int authUserId) {
//        checkNotFoundWithId(repository.delete(id), id);
        checkNotFoundWithId(repository.delete(id, authUserId), id);
    }

    public Meal get(int id, int authUserId) {
//        return checkNotFoundWithId(repository.get(id), id);
//        return null;
        return checkNotFoundWithId(repository.get(id, authUserId), id);
    }

    public List<Meal> getAll(int authUserId) {
        return (List<Meal>) repository.getAll(authUserId);
//        return null;
    }

    public void update(Meal meal, int authUserId) {
//        checkNotFoundWithId(repository.save(user), user.getId());
        checkNotFoundWithId(repository.save(meal, authUserId), meal.getId());
    }
}