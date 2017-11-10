package ru.javawebinar.topjava.web.meal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.List;

@Controller
@Slf4j
public class MealRestController {
    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        return service.save(meal, AuthorizedUser.getId());
    }

    public void update(Meal meal) {
        service.save(meal, AuthorizedUser.getId());
    }

    public void delete(int id) {
        service.delete(id, AuthorizedUser.getId());
    }

    public Meal get(int id) {
        return service.get(id, AuthorizedUser.getId());
    }

    public void save(Meal meal) {
        if (meal.getId()!=null)
            update(meal);
        else
            create(meal);
    }

    public List<MealWithExceed> getAll() {
        return service.getAll(AuthorizedUser.getId(), AuthorizedUser.getDateTimeFilter(), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getAllWithoutFilter() {
        return service.getAll(AuthorizedUser.getId(), AuthorizedUser.getCaloriesPerDay());
    }


}