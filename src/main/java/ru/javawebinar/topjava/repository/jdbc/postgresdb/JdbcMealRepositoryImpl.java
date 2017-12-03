package ru.javawebinar.topjava.repository.jdbc.postgresdb;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.repository.jdbc.AbstractJdbcMealRepositoryImpl;

import java.time.LocalDateTime;

@Profile("postgres")
@Repository
public class JdbcMealRepositoryImpl extends AbstractJdbcMealRepositoryImpl<LocalDateTime> {

//    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
//        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
//    }

    @Override
    public LocalDateTime convertLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
