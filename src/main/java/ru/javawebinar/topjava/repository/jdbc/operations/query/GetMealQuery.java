package ru.javawebinar.topjava.repository.jdbc.operations.query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;

@Component
public class GetMealQuery extends BaseMealQuery {
    private static final String SQL = "select id, description, datetime, calories from meals where id=:id and user_id=:userId";

    public GetMealQuery(@Qualifier("dataSource") DataSource ds) {
        super(ds);
    }

    @Override
    protected String sql() {
        return SQL;
    }

    @Override
    protected void declareParameters() {
        declareParameter(new SqlInOutParameter("id", Types.INTEGER));
        declareParameter(new SqlInOutParameter("userId", Types.INTEGER));
    }
}
