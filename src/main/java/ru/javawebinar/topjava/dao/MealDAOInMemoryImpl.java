package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealDAOInMemoryImpl extends AbstractDAOInMemory<Meal> {

    private static AtomicLong counter = new AtomicLong(0L);
    private static Map<Long, Meal> meals = new ConcurrentHashMap<>();

    private static final DAO<Meal> mealDAOImpl = new MealDAOInMemoryImpl();

    static {
        resetToDefault();
    }

    public static DAO<Meal> getDAO() {
        return mealDAOImpl;
    }

    public static void resetToDefault() {
        meals.clear();
        mealDAOImpl.merge(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealDAOImpl.merge(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealDAOImpl.merge(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealDAOImpl.merge(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealDAOImpl.merge(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealDAOImpl.merge(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    private MealDAOInMemoryImpl() {}

    @Override
    Map<Long, Meal> getTMap() {
        return meals;
    }

    @Override
    Long getId(Meal element) {
        return element.getId() == 0 ? null : element.getId();
    }

    @Override
    Meal setId(Meal element) {
        element.setId(counter.incrementAndGet());
        return element;
    }
}
