package ru.javawebinar.topjava.repository.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

@Repository
@Slf4j
public class InMemoryUserRepositoryImpl implements UserRepository {
    private final Map<Integer, User> repository = new ConcurrentHashMap<>();

    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final User DEFAULT_ADMIN = new User(idCounter.incrementAndGet(), "admin", "admin@foo.bar", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);
    private final User DEFAULT_USER = new User(idCounter.incrementAndGet(), "user", "user@foo.bar", "user", Role.ROLE_ADMIN);
    private final User DEFAULT_USER2 = new User(idCounter.incrementAndGet(), "user2", "user2@foo.bar", "user2", Role.ROLE_ADMIN);

    {
        resetToDefault();
    }

    public void resetToDefault() {
        repository.clear();
        save(DEFAULT_ADMIN);
        save(DEFAULT_USER);
        save(DEFAULT_USER2);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if(user.isNew()) {
            user.setId(idCounter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return repository.values().stream()
                .sorted(Comparator.comparing(User::getName)
                        .thenComparing(User::getId)
                )
                .collect(toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

}
