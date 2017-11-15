package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.DateTimeFilter;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
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
//        MealsUtil.MEALS.forEach(this::create);

        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак Админа", 500), 1);
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед Админа", 1000), 1);
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин Админа", 500), 1);
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак Админа", 1000), 1);
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед Админа", 500), 1);
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин Админа", 510), 1);
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак Пользователя", 500), 2);
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед Пользователя", 1000), 2);
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин Пользователя", 500), 2);
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак Пользователя", 1000), 2);
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед Пользователя", 500), 2);
        create(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин Пользователя", 510), 2);
    }

//    private Meal create(Map.Entry<Integer, Meal> userAndMeal) {
//        return save(userAndMeal.getValue(), userAndMeal.getKey());
//    }

    @Override
    public Meal save(Meal meal, int userId) {
        return meal.isNew() ? create(meal, userId) : update(meal, userId);
    }

    private Meal create(Meal meal, int userId) {
        meal.setId(counter.incrementAndGet());
        getUserMealsRepository(userId).put(meal.getId(), meal);
        return meal;
    }

    private Meal update(Meal meal, int userId) {
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

