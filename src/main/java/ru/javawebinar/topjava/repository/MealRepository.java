package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.DateTimeFilter;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.List;

public interface MealRepository {
    Meal save(Meal meal, int userId);

//    Meal create(Meal meal, int userId);

    boolean delete(int id, int userId);

    Meal get(int id, int userId);

    List<MealWithExceed> getAll(int userId, DateTimeFilter dateTimeFilter, int caloriesPerDay);

    List<MealWithExceed> getAll(int userId, int caloriesPerDay);
}
