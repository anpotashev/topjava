package ru.javawebinar.topjava.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MealWithExceed {
    private LocalDateTime dateTime;

    private String description;

    private int calories;

    private boolean exceed;

    private long mealId;

    public MealWithExceed(long mealId, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
        this.mealId = mealId;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }

}