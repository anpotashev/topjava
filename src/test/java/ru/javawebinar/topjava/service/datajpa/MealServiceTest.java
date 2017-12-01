package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;

@ActiveProfiles("datajpa")
public class MealServiceTest extends AbstractMealServiceTest {


    @Test
    public void testGetWithUser() {
        Meal meal = service.getWithUser(ADMIN_MEAL_ID, UserTestData.ADMIN_ID);
        UserTestData.assertMatch(meal.getUser(), UserTestData.ADMIN);
    }
}
