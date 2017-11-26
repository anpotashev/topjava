package ru.javawebinar.topjava.repository.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
@Slf4j
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        return meal.isNew() ? create(meal, userId) : update(meal, userId);
    }

    private Meal create(Meal meal, int userId) {
        meal.setUser(entityManager.getReference(User.class, userId));
        entityManager.persist(meal);
        return meal;
    }

    private Meal update(Meal meal, int userId) {
        /* One query to db */
//        return entityManager.createNamedQuery(Meal.UPDATE)
//                .setParameter("dateTime", meal.getDateTime())
//                .setParameter("description", meal.getDescription())
//                .setParameter("calories", meal.getCalories())
//                .setParameter("id", meal.getId())
//                .setParameter("userId", userId)
//                .executeUpdate() > 0 ? meal : null;

        /* Two queries to db */
        if (get(meal.getId(), userId) == null)
            return null;
        meal.setUser(entityManager.getReference(User.class, userId));
        return entityManager.merge(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        /* One query to db */
        return entityManager.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() > 0;

        /* Two queries to db */
//        Meal meal = get(id, userId);
//        if (meal == null)
//            return false;
//        entityManager.remove(meal);
//        return true;
    }

    @Override
    public Meal get(int id, int userId) {
        return DataAccessUtils.singleResult(
                entityManager.createNamedQuery(Meal.FIND_BY_ID_AND_USER_ID, Meal.class)
                        .setParameter("id", id)
                        .setParameter("userId", userId)
                        .getResultList()
        );
    }

    @Override
    public List<Meal> getAll(int userId) {
        return entityManager.createNamedQuery(Meal.FIND_ALL_BY_USER_ID, Meal.class)
                .setParameter("userId", userId)
                .getResultList()
                ;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return entityManager.createNamedQuery(Meal.FIND_ALL_BY_USER_ID_BETWEEN_DATES, Meal.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList()
                ;
    }
}