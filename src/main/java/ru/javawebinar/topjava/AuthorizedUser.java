package ru.javawebinar.topjava;

import lombok.extern.slf4j.Slf4j;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

@Slf4j
public class AuthorizedUser {
    private static int id = 1;
    private static final DateTimeFilter dateTimeFilter = new DateTimeFilter();

//    public static int id() {
//        return id;
//    }
    public static int getId() {
        return id;
    }
    public static void setId(int id) {
        AuthorizedUser.id = id;
    }

    public static int getCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static DateTimeFilter getDateTimeFilter() {
        log.debug("getDateTimeFilter");
        return dateTimeFilter;
    }
}