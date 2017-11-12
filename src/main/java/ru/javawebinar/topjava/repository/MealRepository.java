package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.DateTimeFilter;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal save(Meal meal, int userId);

//    Meal create(Meal meal, int userId);

    boolean delete(int id, int userId);

    Meal get(int id, int userId);

    List<Meal> getAllFiltered(int userId, DateTimeFilter dateTimeFilter);

    List<Meal> getAll(int userId);

    Meal create(Meal meal, int userId);
}
