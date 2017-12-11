package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

@Component
@Scope("prototype")
public class UserMapper implements RowMapper {
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
//    private static final BeanPropertyRowMapper<Role> ROLE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Role.class);

    @Nullable
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {

        User user = ROW_MAPPER.mapRow(resultSet, i);
        Role role = Role.valueOf(resultSet.getString("role"));
        user.setRoles(Collections.singleton(role));
        return user;

    }
}
