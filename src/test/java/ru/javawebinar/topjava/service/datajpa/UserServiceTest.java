package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

@ActiveProfiles("datajpa")
public class UserServiceTest extends AbstractUserServiceTest {

    @Test
    public void testGetWithMeals() {
        User user = service.getWithMeals(UserTestData.ADMIN_ID);
        MealTestData.assertMatch(user.getMeals(), MealTestData.ADMIN_MEAL2, MealTestData.ADMIN_MEAL1);
    }
}
