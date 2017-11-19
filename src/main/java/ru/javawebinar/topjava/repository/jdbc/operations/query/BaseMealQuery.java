package ru.javawebinar.topjava.repository.jdbc.operations.query;

import org.springframework.jdbc.object.MappingSqlQueryWithParameters;
import ru.javawebinar.topjava.model.Meal;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

public abstract class BaseMealQuery extends MappingSqlQueryWithParameters<Meal> {

    public BaseMealQuery(DataSource ds) {
        super();
        setDataSource(ds);
        setSql(sql());
        declareParameters();
    }

    protected abstract String sql();

    protected abstract void declareParameters();

    @Override
    public Meal mapRow(ResultSet resultSet, int i, Object[] objects, Map<?, ?> map) throws SQLException {
        Meal meal = new Meal();
        meal.setId(resultSet.getInt("id"));
        meal.setDescription(resultSet.getString("description"));
        meal.setDateTime(LocalDateTime.ofInstant(
                ((Timestamp) resultSet.getObject("datetime")).toInstant(), ZoneId.systemDefault()
        ));
        meal.setCalories(resultSet.getInt("calories"));
        return meal;
    }
}
