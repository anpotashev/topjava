package ru.javawebinar.topjava.web.meal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.DateTimeFilter;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
@Slf4j
public class MealRestController {
    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        log.debug("create");
        return service.create(meal, AuthorizedUser.getId());
    }

    public void delete(int id) {
        service.delete(id, AuthorizedUser.getId());
    }

    public Meal get(int id) {
        return service.get(id, AuthorizedUser.getId());
    }

    public void save(Meal meal, int id) {
        log.debug("save");
        assureIdConsistent(meal, id); //пока не имеет смысла
//        Meal meal1 = meal.isNew() ? service.create(meal, AuthorizedUser.getId()) : service.save(meal, AuthorizedUser.getId());
        service.save(meal, AuthorizedUser.getId());
    }

    public List<MealWithExceed> getAllFiltered(DateTimeFilter filter) {
        return service.getAllFiltered(AuthorizedUser.getId(), filter, AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getAll() {
        return service.getAll(AuthorizedUser.getId(), AuthorizedUser.getCaloriesPerDay());
    }

}