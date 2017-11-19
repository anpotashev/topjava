package ru.javawebinar.topjava.repository.jdbc.operations.query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;

@Component
public class DeleteMealQuery extends SqlUpdate {
    private static final String SQL = "DELETE FROM meals WHERE id=:id AND user_id=:userId";

    public DeleteMealQuery(@Qualifier("dataSource") DataSource ds) {
        super();
        setDataSource(ds);
        setSql(SQL);
        declareParameters();
    }

    protected String sql() {
        return SQL;
    }

    protected void declareParameters() {
        declareParameter(new SqlInOutParameter("userId", Types.INTEGER));
        declareParameter(new SqlInOutParameter("id", Types.INTEGER));
    }
}
