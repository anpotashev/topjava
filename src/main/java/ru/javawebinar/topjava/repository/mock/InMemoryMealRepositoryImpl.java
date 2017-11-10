package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.DateTimeFilter;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.List;
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

    private Meal save(Map.Entry<Integer, Meal> userAndMeal) {
        return save(userAndMeal.getValue(), userAndMeal.getKey());
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else {
            if (getUserMealsRepository(userId).get(meal.getId()) == null)
                return null;
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
    public List<MealWithExceed> getAll(int userId, DateTimeFilter dateTimeFilter, int caloriesPerDay) {
        return MealsUtil.getFilteredWithExceeded(
                getUserMealsRepository(userId).values().stream()
                .filter(s -> checkDate(s, dateTimeFilter))
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(toList()), dateTimeFilter.getStartTime(), dateTimeFilter.getEndTime(), caloriesPerDay);
    }

    @Override
    public List<MealWithExceed> getAll(int userId, int caloriesPerDay) {
        return getAll(userId, new DateTimeFilter(), caloriesPerDay);
    }

    private Map<Integer, Meal> getUserMealsRepository(int userId) {
        Map<Integer, Meal> result = repository.get(userId);
        if (result == null) {
            result = new ConcurrentHashMap<>();
            repository.put(userId, result);
        }
        return result;
    }

    private static boolean checkDate(Meal meal, DateTimeFilter dateTimeFilter) {
        LocalDate checkDate = meal.getDate();
        return DateTimeUtil.isBetween(
                meal.getDateTime().toLocalDate()
                , dateTimeFilter.getStartDate()
                , dateTimeFilter.getEndDate()
        );
    }
}

