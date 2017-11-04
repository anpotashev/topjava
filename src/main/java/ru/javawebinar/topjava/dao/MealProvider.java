package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MealProvider {

    private static AtomicLong counter = new AtomicLong(0);

    private static final List<Meal> meals = new LinkedList<>();

    static {
        resetToDefautl();
    }

    public static List<Meal> getMeals() {
        return meals;
    }

    public static void resetToDefautl() {
        meals.clear();
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static Meal add(Meal meal) {
        if (meal.getId() == 0) {
            meal.setId(counter.addAndGet(1));
        }
        if (!meals.contains(meal))
            meals.add(meal);
        return meal;
    }
}
