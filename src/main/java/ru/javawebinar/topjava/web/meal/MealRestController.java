package ru.javawebinar.topjava.web.meal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class MealRestController {
    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        return service.create(meal);
    }

    public void update(Meal meal) {
        service.update(meal, AuthorizedUser.getId());
    }

    public void delete(int id) {
        service.delete(id, AuthorizedUser.getId());
    }

    public Meal get(int id) {
        return service.get(id, AuthorizedUser.getId());
    }

//    public List<MealWithExceed> getAll() {
////        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.getId())
////            , LocalTime.MIN, LocalTime.MAX, AuthorizedUser.getCaloriesPerDay());
//        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.getId())
//                , AuthorizedUser.getStartTime(), AuthorizedUser.getEndTime(), AuthorizedUser.getCaloriesPerDay()).stream()
//                .filter(s -> isDateBetween(s))
//                .collect(Collectors.toList());
//    }

    public void save(Meal meal) {
        if (meal.getId()!=null)
            update(meal);
        else
            create(meal);
    }

    public List<MealWithExceed> getAll() {
        return MealsUtil.getFilteredWithExceeded(service.getAll(AuthorizedUser.getId())
                , AuthorizedUser.getDateTimeFilter().getStartTime(), AuthorizedUser.getDateTimeFilter().getEndTime(), AuthorizedUser.getCaloriesPerDay()).stream()
                .filter(MealRestController::checkDate)
                .collect(Collectors.toList());
    }

//    private static boolean isDateBetween(MealWithExceed mealWithExceed, LocalDate startDate, LocalDate endDate) {
//        LocalDate checkDate = mealWithExceed.getDateTime().toLocalDate();
//        boolean result = ((startDate == null) | (checkDate.compareTo(startDate)>=0));
//        result &= ((endDate== null) | (endDate.compareTo(checkDate) >=0));
//        return result;
//    }

    private static boolean checkDate(MealWithExceed mealWithExceed) {
        LocalDate checkDate = mealWithExceed.getDateTime().toLocalDate();
        log.debug("checkDate");
        boolean result = ((AuthorizedUser.getDateTimeFilter().getStartDate() == null) || (checkDate.compareTo(AuthorizedUser.getDateTimeFilter().getStartDate())>=0));
        result &= ((AuthorizedUser.getDateTimeFilter().getEndDate() == null) || (AuthorizedUser.getDateTimeFilter().getEndDate().compareTo(checkDate) >=0));
        log.debug("end checkDate");
        return result;
    }

}