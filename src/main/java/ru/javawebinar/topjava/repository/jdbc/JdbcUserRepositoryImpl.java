package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

//    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private static final int[] ROLES_UPDATE_BATCH_TIPES = new int[] {Types.INTEGER, Types.VARCHAR};
    @Autowired
    private UserMapper userMapper;

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
                return null;
            }
            deleteRoles(user.getId());
        }
        saveRoles(user);
        return user;
    }

    private void deleteRoles(Integer id) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", id);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id WHERE id=?", userMapper, id);
        users = unionRoles(users);
        User user = DataAccessUtils.singleResult(users);
        return user;
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON user_roles.user_id = users.id WHERE email=?", userMapper, email);
        users = unionRoles(users);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON user_roles.user_id = users.id ORDER BY name, email", userMapper);
        users = unionRoles(users);
        return users;
    }

    private List<User> unionRoles(List<User> users) {
        List<User> result = new LinkedList<>();
        users.forEach(user -> {
                    if (result.contains(user)) {
                        result.get(result.size()-1).getRoles().addAll(user.getRoles());
                    } else {
                        result.add(user);
                    }

                });
        return result;
    }

    private void saveRoles(User user) {
        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)"
                , prepareBatchArgumentsList(user)
                , ROLES_UPDATE_BATCH_TIPES);
    }

    private List<Object[]> prepareBatchArgumentsList(User user) {
        return user.getRoles().stream()
                .map(role -> new Object[] {user.getId(), role.toString()})
                .collect(Collectors.toList());
    }

}
