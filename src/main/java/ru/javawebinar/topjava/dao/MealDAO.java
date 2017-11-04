package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    public Meal add(Meal meal);
    public Meal edit(Meal meal);
    public void delete(Meal meal);
    public List<Meal> findAll();
    public Meal findById(Long id);
}
