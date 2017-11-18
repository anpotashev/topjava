package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    /* Sorting by desc by date then asc by time: */
//    private static final String SORT_SQL_STRING = " ORDER BY date_trunc('day', datetime) DESC, datetime";

    /* Sorting by desc by datetime: */
    private static final String SORT_SQL_STRING = " ORDER BY datetime DESC";

    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.insertMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        return meal.isNew() ? create(meal, userId) : update(meal, userId);

    }

    private Meal create(Meal meal, int userId) {
        MapSqlParameterSource map = createParameterMap(meal, userId);
        Number id = insertMeal.executeAndReturnKey(map);
        meal.setId(id.intValue());
        return meal;
    }

    private Meal update(Meal meal, int userId) {
        MapSqlParameterSource map = createParameterMap(meal, userId);
        return namedParameterJdbcTemplate.update("update meals set " +
                "datetime = :datetime, " +
                "description = :description, " +
                "calories = :calories " +
                "where id = :id and " +
                "user_id = :userId", map) != 0 ? meal : null;
    }

    private MapSqlParameterSource createParameterMap(Meal meal, int userId) {
        return new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("datetime", meal.getDateTime())
                .addValue("calories", meal.getCalories())
                .addValue("userId", userId)
                ;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("delete from meals WHERE id=? and user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query("select id, datetime, description, calories from meals where id=? and user_id=?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("select id, datetime, description, calories from meals where user_id=?" + SORT_SQL_STRING, ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        StringBuilder sqlBuilder = new StringBuilder("select * from meals where user_id=?");
        appendDateToSQL(startDate, true, sqlBuilder);
        appendDateToSQL(endDate, false, sqlBuilder);
        sqlBuilder.append(SORT_SQL_STRING);
        return jdbcTemplate.query(sqlBuilder.toString(), ROW_MAPPER, userId);
    }

    private void appendDateToSQL(LocalDateTime dateTime, boolean isStartDate, StringBuilder builder) {
//        if (dateTime != null) {
            builder.append(" and datetime ")
                    .append(isStartDate ? " >= '" : " <= '")
                    .append(dateTime.toLocalDate().toString())
                    .append("' ");
//        }
    }
}
