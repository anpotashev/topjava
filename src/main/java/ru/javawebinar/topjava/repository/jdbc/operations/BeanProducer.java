package ru.javawebinar.topjava.repository.jdbc.operations;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.repository.jdbc.operations.query.*;

@Component
public abstract class BeanProducer {
    @Lookup
    public abstract CreateMealQuery getCreateMealQuery();

    @Lookup
    public abstract DeleteMealQuery getDeleteMealQuery();

    @Lookup
    public abstract GetMealQuery getMealQuery();

    @Lookup
    public abstract GetMealsBetweenQuery getMealsBetweenQuery();

    @Lookup
    public abstract GetMealsQuery getMealsQuery();

    @Lookup
    public abstract SaveMealQuery getSaveMealQuery();

    @Lookup
    public abstract ParameterBuilder getParameterBuilder();

}
