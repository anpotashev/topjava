package ru.javawebinar.topjava.dao;

import lombok.extern.slf4j.Slf4j;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

@Slf4j
public class MealDAOSimpleImpl implements MealDAO {

    private static final CRUD<Meal> mealCRUD = MealInMemoryCRUDImpl.getMealInMemoryCRUD();

    @Override
    public Meal add(Meal meal) {
        return mealCRUD.create(meal);
    }

    @Override
    public Meal edit(Meal meal) {
        return mealCRUD.update(meal);
    }

    @Override
    public void delete(Meal meal) {
        mealCRUD.delete(meal.getId());
    }

    @Override
    public List<Meal> findAll() {
        return mealCRUD.findAll();
    }

    @Override
    public Meal findById(Long id) {
        return mealCRUD.findById(id);
    }

    public static void main(String[] args) {
        MealDAO mealDAO = new MealDAOSimpleImpl();
        System.out.println(mealDAO.findAll());
    }
}
