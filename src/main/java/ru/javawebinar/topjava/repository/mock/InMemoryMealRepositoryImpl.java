package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }


    /*just for simplification of init block */
    private void save(Meal meal) {
        save(meal, meal.getUserId());
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        getUserMealsRepository(userId).put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return getUserMealsRepository(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return getUserMealsRepository(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).values().stream()
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(toList());
    }

    private Map<Integer, Meal> getUserMealsRepository(int userId) {
        Map<Integer, Meal> result = repository.get(userId);
        if (result == null) {
            result = new ConcurrentHashMap<>();
            repository.put(userId, result);
        }
        return result;
    }
}

