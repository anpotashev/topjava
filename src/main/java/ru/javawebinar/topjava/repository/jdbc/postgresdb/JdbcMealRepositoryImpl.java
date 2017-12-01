package ru.javawebinar.topjava.repository.jdbc.postgresdb;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.repository.jdbc.AbstractJdbcMealRepositoryImpl;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Profile("postgres")
@Repository
public class JdbcMealRepositoryImpl extends AbstractJdbcMealRepositoryImpl {

    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Object convertLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
