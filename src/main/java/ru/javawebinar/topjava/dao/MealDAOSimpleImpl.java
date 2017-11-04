package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealDAOSimpleImpl implements MealDAO {


//    private static final MealProvider mealProvider = new MealProvider();

    @Override
    public Meal add(Meal meal) {
        MealProvider.add(meal);
        return meal;
    }

    @Override
    public Meal edit(Meal meal) {
        return meal;
    }

    @Override
    public void delete(Meal meal) {
        MealProvider.getMeals().remove(meal);
    }

    @Override
    public List<Meal> findAll() {
        return MealProvider.getMeals();
    }

    @Override
    public Meal findById(Long id) {
        for (Meal meal : findAll())
            if (meal.getId().equals(id))
                return meal;
        return null;
    }

    public static void main(String[] args) {
        MealDAO mealDAO = new MealDAOSimpleImpl();
        System.out.println(mealDAO.findAll());
    }
}
