package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.LinkedList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithIdUserId;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal create(Meal meal) {
        return repository.save(meal, meal.getUserId());
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithIdUserId(repository.delete(id, userId), id, userId);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithIdUserId(repository.get(id, userId), id, userId);
    }

    @Override
    public void update(Meal meal, int userId) {
        repository.save(meal, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return new LinkedList<>(repository.getAll(userId));
    }

}