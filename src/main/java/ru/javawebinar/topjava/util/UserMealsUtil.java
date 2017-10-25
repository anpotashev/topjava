package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)
                .stream()
                .forEach(System.out::println)
        ;
        getFilteredWithExceededByCycle(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)
                .stream()
                .forEach(System.out::println)
        ;
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateCaloriesMap = mealList.stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(userMeal -> (
                                TimeUtil.isBetween(
                                        userMeal.getDateTime().toLocalTime()
                                        , startTime
                                        , endTime.plusNanos(1L))
                        )
                )
                .map(
                        userMeal -> new UserMealWithExceed(userMeal.getDateTime()
                                , userMeal.getDescription()
                                , userMeal.getCalories()
                                , dateCaloriesMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)
                )
                .collect(Collectors.toList())
                ;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dateCaloriesMap = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            LocalDate date = userMeal.getDateTime().toLocalDate();
            dateCaloriesMap.merge(date, userMeal.getCalories(), (a, b) -> a + b);
        }

        List<UserMealWithExceed> result = new LinkedList<>();
        for (UserMeal userMeal : mealList) {
            LocalTime time = userMeal.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(
                    time
                    , startTime
                    , endTime.plusNanos(1L))
                    ) {
                LocalDateTime dateTime = userMeal.getDateTime();
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(
                        dateTime
                        , userMeal.getDescription()
                        , userMeal.getCalories()
                        , dateCaloriesMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay
                );
                result.add(userMealWithExceed);
            }
        }
        return result;
    }
}
