package ru.javawebinar.topjava.repository.jdbc.operations.query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;

@Component
public class GetMealsBetweenQuery extends BaseMealQuery {
    private static final String SQL = "select id, description, datetime, calories from meals where user_id=:userId and datetime between :startDateTime and :endDateTime";

    public GetMealsBetweenQuery(@Qualifier("dataSource") DataSource ds) {
        super(ds);
    }

    @Override
    protected String sql() {
        return SQL;
    }

    @Override
    protected void declareParameters() {
        declareParameter(new SqlInOutParameter("userId", Types.INTEGER));
        declareParameter(new SqlInOutParameter("startDateTime", Types.TIMESTAMP));
        declareParameter(new SqlInOutParameter("endDateTime", Types.TIMESTAMP));
    }
}
