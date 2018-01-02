package ru.javawebinar.topjava.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;

import java.util.List;

public interface UserRepository {
    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();

    default User getWithMeals(int id) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    default User setState(int userId, boolean newState) {
        User user = get(userId);
        user.setEnabled(newState);
        return save(user);
    }

    ;
}