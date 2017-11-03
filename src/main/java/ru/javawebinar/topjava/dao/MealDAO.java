package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    public Meal addMeal(Meal meal);
    public Meal editMeal(Meal meal);
    public void deleteMeal(Meal meal);
    public List<Meal> findAllMeal();
    public Meal findMealById(Long id);
}
