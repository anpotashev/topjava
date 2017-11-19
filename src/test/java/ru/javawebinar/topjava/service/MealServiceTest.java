package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void getWithRightUserIdAndMealId() throws Exception {
        Meal meal = mealService.get(START_MEAL_ID, USER_ID);
        assertMatch(meal, MEAL_USER_ID_100000_ID_100002);
    }

    @Test(expected = NotFoundException.class)
    public void getWithWrongUserIdAndMealId() throws Exception {
        mealService.get(START_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void deleteWithRightUserIdAndMealId() throws Exception {
        mealService.delete(START_MEAL_ID, USER_ID);
        assertMatch(mealService.getAll(USER_ID), MEAL_USER_ID_100000_ID_100003, MEAL_USER_ID_100000_ID_100004, MEAL_USER_ID_100000_ID_100005, MEAL_USER_ID_100000_ID_100006, MEAL_USER_ID_100000_ID_100007);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWithWrongUserIdAndMealId() throws Exception {
        mealService.delete(START_MEAL_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        LocalDate date = LocalDate.of(2015, Month.MAY, 31);
        List<Meal> meals = mealService.getBetweenDates(date, date, USER_ID);
        assertMatch(meals, MEAL_USER_ID_100000_ID_100002, MEAL_USER_ID_100000_ID_100003, MEAL_USER_ID_100000_ID_100004);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        LocalDate date = LocalDate.of(2015, Month.MAY, 31);
        LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(date, LocalTime.MAX);
        List<Meal> meals = mealService.getBetweenDateTimes(startDateTime, endDateTime, USER_ID);
        assertMatch(meals, MEAL_USER_ID_100000_ID_100002, MEAL_USER_ID_100000_ID_100003, MEAL_USER_ID_100000_ID_100004);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = mealService.getAll(USER_ID);
        assertMatch(meals, MEAL_USER_ID_100000_ID_100002, MEAL_USER_ID_100000_ID_100003, MEAL_USER_ID_100000_ID_100004, MEAL_USER_ID_100000_ID_100005, MEAL_USER_ID_100000_ID_100006, MEAL_USER_ID_100000_ID_100007);
    }

    @Test
    public void updategetWithRightUserIdAndMealId() throws Exception {
        Meal meal = new Meal(START_MEAL_ID, LocalDateTime.of(2017, Month.NOVEMBER, 1, 11, 00), "Проба", 200);
        mealService.update(meal, USER_ID);
        assertMatch(mealService.get(START_MEAL_ID, USER_ID), meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateWithWrongUserIdAndMelaId() {
        Meal meal = new Meal(START_MEAL_ID, LocalDateTime.of(2017, Month.NOVEMBER, 1, 11, 00), "Проба", 200);
        mealService.update(meal, ADMIN_ID);
    }

    //    Тест свалится на inMemory-реализации
//    @Ignore
    @Test(expected = DuplicateKeyException.class)
    public void updateWithDuplicateUserIdAndDatetime() {
        Meal meal = new Meal(START_MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 00), "Проба", 200);
        mealService.update(meal, USER_ID);
    }


    @Test
    public void create() throws Exception {
        Meal meal = new Meal(LocalDateTime.of(2017, Month.NOVEMBER, 1, 11, 00), "Проба", 200);
        mealService.update(meal, USER_ID);
        meal.setId(100014);
        assertMatch(mealService.get(100014, USER_ID), meal);
    }

    //    Тест свалится на inMemory-реализации
//    @Ignore
    @Test(expected = DuplicateKeyException.class)
    public void createWithDuplicateUserIdAndDatetime() throws Exception {
        Meal meal = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 00), "Проба", 200);
        mealService.update(meal, USER_ID);
    }

}