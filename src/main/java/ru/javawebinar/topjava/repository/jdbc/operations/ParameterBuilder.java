package ru.javawebinar.topjava.repository.jdbc.operations;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public class ParameterBuilder {
    Map<String, Object> parameters = new HashMap();

    public ParameterBuilder addId(int id) {
        parameters.put("id", id);
        return this;
    }

    public ParameterBuilder addUserId(int userId) {
        parameters.put("userId", userId);
        return this;
    }

    public ParameterBuilder addMeal(Meal meal) {
        if (!meal.isNew())
            parameters.put("id", meal.getId());
        parameters.put("datetime", meal.getDateTime());
        parameters.put("description", meal.getDescription());
        parameters.put("calories", meal.getCalories());
        return this;
    }

    public ParameterBuilder addStartAndEndDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        parameters.put("startDateTime", startDate);
        parameters.put("endDateTime", endDate);
        return this;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
