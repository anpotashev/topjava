package ru.javawebinar.topjava.repository.jdbc.operations.query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;

@Component
public class GetMealsQuery extends BaseMealQuery {
    private static final String SQL = "select id, description, datetime, calories from meals where user_id=:userId";

    public GetMealsQuery(@Qualifier("dataSource") DataSource ds) {
        super(ds);
    }

    @Override
    protected String sql() {
        return SQL;
    }

    @Override
    protected void declareParameters() {
        super.declareParameter(new SqlInOutParameter("userId", Types.INTEGER));
    }
}
