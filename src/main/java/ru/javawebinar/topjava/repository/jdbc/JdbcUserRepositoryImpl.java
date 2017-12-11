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
        System.out.println("before");
        user.getRoles().forEach(role -> System.out.println(role.toString()));
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            System.out.println("after");
            user.getRoles().forEach(role -> System.out.println(role.toString()));
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        saveRoles(user);
        return user;
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
//        if (user != null) {
//            loadRoles(user);
//        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users LEFT JOIN user_roles ON user_roles.user_id = users.id WHERE email=?", userMapper, email);
//        users.forEach(user -> loadRoles(user));
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

//    private void loadRoles(List<User> users) {
//        List<Integer> userIds = users.stream().map(u -> u.getId()).collect(Collectors.toList());
//        jdbcTemplate.query("select id, role from users where id in ")
//
//    }

//    private void loadRoles(User user) {
//        Set<Role> roles =  jdbcTemplate.queryForList("SELECT role FROM user_roles WHERE user_id=?", String.class, user.getId())
//                .stream()
//                .map(s -> Role.valueOf(s))
//                .collect(Collectors.toSet());
//        user.setRoles(roles);
//    }

    private void saveRoles(User user) {
        int c = jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());

        List<Object[]> batch =  user.getRoles().stream().map(role -> new Object[] {user.getId(), role.toString()})
                .collect(Collectors.toList());
        int[] types = new int[] {Types.INTEGER, Types.VARCHAR};

        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)", batch, types);
    }

}
