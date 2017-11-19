package ru.javawebinar.topjava.repository.jdbc.operations.query;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Types;

@Slf4j
@Component
public class CreateMealQuery extends SqlUpdate {
    private static final String SQL = "INSERT INTO meals (datetime, description, calories, user_id) " +
            "VALUES (:datetime, :description, :calories, :userId)";

    public CreateMealQuery(@Qualifier("dataSource") DataSource ds) {
        super();
        setSql(SQL);
        setGeneratedKeysColumnNames("id");
        setReturnGeneratedKeys(true);
        setDataSource(ds);
        declareParameters();
    }

    protected String sql() {
        return SQL;
    }

    protected void declareParameters() {
        declareParameter(new SqlInOutParameter("userId", Types.INTEGER));
        declareParameter(new SqlInOutParameter("description", Types.VARCHAR));
        declareParameter(new SqlInOutParameter("datetime", Types.TIMESTAMP));
        declareParameter(new SqlInOutParameter("calories", Types.INTEGER));
    }
}
