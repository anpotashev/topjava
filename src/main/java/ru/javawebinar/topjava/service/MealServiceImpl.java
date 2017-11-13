package ru.javawebinar.topjava.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.DateTimeFilter;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithIdUserId;

@Service
@Slf4j
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithIdUserId(repository.delete(id, userId), id, userId);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithIdUserId(repository.get(id, userId), id, userId);
    }

    @Override
    public Meal save(Meal meal, int userId) throws NotFoundException {
        log.debug("save");
        return checkNotFoundWithIdUserId(
                repository.save(meal, userId),
                meal.getId(),
                userId);
    }

    @Override
    public Meal create(Meal meal, int userId) {
        log.debug("create");
        return repository.save(meal, userId);
    }

    @Override
    public List<MealWithExceed> getAllFiltered(int userId, DateTimeFilter dateTimeFilter, int caloriesPerDay) {
        return MealsUtil.getFilteredWithExceeded(
                repository.getAllFiltered(userId, dateTimeFilter)
                , dateTimeFilter.getStartTime()
                , dateTimeFilter.getEndTime()
                , caloriesPerDay
        );
    }

    @Override
    public List<MealWithExceed> getAll(int userId, int caloriesPerDay) {
        return MealsUtil.getWithExceeded(
                repository.getAll(userId)
                , caloriesPerDay
        );
    }

}