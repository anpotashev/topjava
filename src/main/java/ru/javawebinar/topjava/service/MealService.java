package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.DateTimeFilter;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {

//    Meal create(Meal meal);

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    Meal save(Meal meal, int userId) throws NotFoundException;

    List<MealWithExceed> getAll(int userId, DateTimeFilter dateTimeFilter, int caloriesPerDay);

    List<MealWithExceed> getAll(int userId, int caloriesPerDay);
}