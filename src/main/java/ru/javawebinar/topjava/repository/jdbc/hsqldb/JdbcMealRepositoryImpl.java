package ru.javawebinar.topjava.repository.jdbc.hsqldb;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.repository.jdbc.AbstractJdbcMealRepositoryImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Profile("hsqldb")
@Repository
public class JdbcMealRepositoryImpl extends AbstractJdbcMealRepositoryImpl<Timestamp> {

//    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
//        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
//    }

    @Override
    public Timestamp convertLocalDateTime(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }
}
