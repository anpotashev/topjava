package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealInMemoryCRUDImpl implements CRUD<Meal> {

    private static AtomicLong counter = new AtomicLong(0);

    private static final Map<Long, Meal> meals = new ConcurrentHashMap<>();

    private static final MealInMemoryCRUDImpl mealInMemoryCRUD = new MealInMemoryCRUDImpl();

    static {
        resetToDefault();
    }

    public static void resetToDefault() {
        meals.clear();
        mealInMemoryCRUD.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealInMemoryCRUD.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealInMemoryCRUD.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealInMemoryCRUD.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealInMemoryCRUD.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealInMemoryCRUD.create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static CRUD<Meal> getMealInMemoryCRUD() {
        return mealInMemoryCRUD;
    }

    @Override
    public Meal create(Meal meal) {
        if (meal.getId() == 0L) {
            meal.setId(counter.addAndGet(1));
        }
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(Meal entity) {
        meals.remove(entity.getId());
    }

    @Override
    public void delete(long id) {
        meals.remove(id);
    }

    @Override
    public Meal findById(long id) {
        return meals.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        if (meal.getId() != 0L) {
            meals.put(meal.getId(), meal);
        }
        return meal;
    }

    @Override
    public List<Meal> findAll() {
        return new LinkedList<>(meals.values());
    }
}
