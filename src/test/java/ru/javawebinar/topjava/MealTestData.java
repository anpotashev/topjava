package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

public class MealTestData {
    private static final AtomicInteger counter = new AtomicInteger(ADMIN_ID);
    public static final int START_MEAL_ID = ADMIN_ID+1;

    public static final List<Meal> USERS_MEAL = Arrays.asList(

            new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин пользователя", 510),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Обед пользователя", 1000),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Завтрак пользователя", 500),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин пользователя", 500),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Обед пользователя", 1000),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Завтрак пользователя", 500)

            );

    public static final List<Meal> ADMINS_MEAL = Arrays.asList(
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин админа", 510),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Обед админа", 1000),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Завтрак админа", 500),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин админа", 500),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Обед админа", 1000),
            new Meal(counter.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Завтрак админа", 500)
    
    );


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
