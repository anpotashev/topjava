package ru.javawebinar.topjava.repository.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.DateTimeFilter;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Repository
@Slf4j
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::create);
    }

    private Meal create(Map.Entry<Integer, Meal> userAndMeal) {
        return create(userAndMeal.getValue(), userAndMeal.getKey());
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = getUserMealsRepository(userId);
        return meals.get(meal.getId()) == null ? null : meals.put(meal.getId(), meal);
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
    public List<Meal> getAllFiltered(int userId, DateTimeFilter dateTimeFilter) {
        return getUserMealsRepository(userId).values().stream()
                .filter(s -> checkDate(s, dateTimeFilter))
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(toList());
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllFiltered(userId, new DateTimeFilter());
    }

    @Override
    public Meal create(Meal meal, int userId) {
        meal.setId(counter.incrementAndGet());
        return getUserMealsRepository(userId).put(meal.getId(), meal);
    }

    private Map<Integer, Meal> getUserMealsRepository(int userId) {
        return repository.computeIfAbsent(userId, k -> new ConcurrentHashMap<Integer, Meal>());
    }

    private static boolean checkDate(Meal meal, DateTimeFilter dateTimeFilter) {
        return DateTimeUtil.isBetween(
                meal.getDateTime().toLocalDate()
                , dateTimeFilter.getStartDate()
                , dateTimeFilter.getEndDate()
        );
    }
}

