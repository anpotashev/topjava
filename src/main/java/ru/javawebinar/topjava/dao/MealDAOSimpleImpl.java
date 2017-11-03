package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealDAOSimpleImpl implements MealDAO {


    private static final MealProvider mealProvider = new MealProvider();

    @Override
    public Meal addMeal(Meal meal) {
        mealProvider.getMeals().add(meal);
        return meal;
    }

    @Override
    public Meal editMeal(Meal meal) {
        return meal;
    }

    @Override
    public void deleteMeal(Meal meal) {
        mealProvider.getMeals().remove(meal);
    }

    @Override
    public List<Meal> findAllMeal() {
        return mealProvider.getMeals();
    }

    @Override
    public Meal findMealById(Long id) {
        for (Meal meal : findAllMeal())
            if (meal.getId().equals(id))
                return meal;
        return null;
    }

    public static void main(String[] args) {
        MealDAO mealDAO = new MealDAOSimpleImpl();
        System.out.println(mealDAO.findAllMeal());
    }
}
