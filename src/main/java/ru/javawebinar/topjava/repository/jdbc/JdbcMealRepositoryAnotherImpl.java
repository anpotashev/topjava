package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.jdbc.operations.BeanProducer;
import ru.javawebinar.topjava.repository.jdbc.operations.query.BaseMealQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcMealRepositoryAnotherImpl implements MealRepository {

    @Autowired
    private BeanProducer beanProducer;

    @Override
    public Meal save(Meal meal, int userId) {
        return meal.isNew() ? create(meal, userId) : update(meal, userId);
    }

    private Meal create(Meal meal, int userId) {
        SqlUpdate query = beanProducer.getCreateMealQuery();
        Map<String, Object> parameters = beanProducer.getParameterBuilder()
                .addMeal(meal).addUserId(userId).getParameters();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        query.updateByNamedParam(parameters, keyHolder);
        meal.setId(keyHolder.getKey().intValue());
        return meal;
    }

    private Meal update(Meal meal, int userId) {
        SqlUpdate query = beanProducer.getSaveMealQuery();
        Map<String, Object> parameters = beanProducer.getParameterBuilder()
                .addMeal(meal).addUserId(userId).getParameters();
        boolean result = query.updateByNamedParam(parameters) > 0;
        return result ? meal : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        SqlUpdate query = beanProducer.getDeleteMealQuery();
        Map<String, Object> parameters = beanProducer.getParameterBuilder()
                .addId(id).addUserId(userId).getParameters();
        return query.updateByNamedParam(parameters) > 0;
    }

    @Override
    public Meal get(int id, int userId) {
        BaseMealQuery query = beanProducer.getMealQuery();
        Map<String, Object> parameters = beanProducer.getParameterBuilder()
                .addId(id).addUserId(userId).getParameters();
        return query.findObjectByNamedParam(parameters);
    }

    @Override
    public List<Meal> getAll(int userId) {
        BaseMealQuery query = beanProducer.getMealsQuery();
        Map<String, Object> parameters = beanProducer.getParameterBuilder()
                .addUserId(userId).getParameters();
        return query.executeByNamedParam(parameters);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        BaseMealQuery query = beanProducer.getMealsBetweenQuery();
        Map<String, Object> parameters = beanProducer.getParameterBuilder()
                .addUserId(userId).addStartAndEndDateTime(startDate, endDate).getParameters();
        return query.executeByNamedParam(parameters);
    }
}
