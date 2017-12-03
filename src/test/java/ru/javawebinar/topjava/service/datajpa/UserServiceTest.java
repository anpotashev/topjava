package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Collections;

@ActiveProfiles("datajpa")
public class UserServiceTest extends AbstractUserServiceTest {

    @Test
    public void testGetWithMeals() {
        User user = service.getWithMeals(UserTestData.ADMIN_ID);
        MealTestData.assertMatch(user.getMeals(), MealTestData.ADMIN_MEAL2, MealTestData.ADMIN_MEAL1);
    }

    @Test
    public void testGetWithEmptyMealsList() {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, Collections.singleton(Role.ROLE_USER));
        newUser = service.create(newUser);
        User user = service.getWithMeals(newUser.getId());
        Assert.assertNotNull(user.getMeals());
        Assert.assertEquals(user.getMeals().size(), 0);
    }
}
