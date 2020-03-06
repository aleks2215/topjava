package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 8;

    public static final Meal USER_MEAL = new Meal(USER_MEAL_ID, LocalDateTime.of(2020, Month.MARCH, 6, 9, 0), "Завтрак", 600);;
    public static final Meal ADMIN_MEAL = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2020, Month.MARCH, 8, 9, 0), "Завтрак", 10000);

    public static final List<Meal> ALL_USER_MEALS = Arrays.asList(
            new Meal(USER_MEAL_ID + 4, LocalDateTime.of(2020, Month.MARCH, 7, 13, 0), "Обед", 2000),
            new Meal(USER_MEAL_ID + 3, LocalDateTime.of(2020, Month.MARCH, 7, 9, 0), "Завтрак", 1000),
            new Meal(USER_MEAL_ID + 2, LocalDateTime.of(2020, Month.MARCH, 6, 19, 0), "Ужин", 1500),
            new Meal(USER_MEAL_ID + 1, LocalDateTime.of(2020, Month.MARCH, 6, 13, 0), "Обед", 1000),
            new Meal(USER_MEAL_ID, LocalDateTime.of(2020, Month.MARCH, 6, 9, 0), "Завтрак", 600)
    );

    public static Meal getNewUser() {
        return new Meal(null, LocalDateTime.of(2020, Month.MARCH, 9, 9, 0), "Завтрак", 600);
    }

    public static Meal getNewAdmin() {
        return new Meal(null, LocalDateTime.of(2020, Month.MARCH, 10, 9, 0), "Завтрак", 10000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(USER_MEAL);
        updated.setDescription("Суперполдник");
        updated.setCalories(100000);
        return updated;
    }

}
