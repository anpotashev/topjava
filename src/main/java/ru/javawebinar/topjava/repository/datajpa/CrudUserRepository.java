package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
//    @Query(name = User.DELETE)
//    @Query("DELETE FROM User u WHERE u.id=:id")
    int removeById(int id);

    @Override
    @Transactional
    User save(User user);

    @Override
    Optional<User> findById(Integer id);

    @EntityGraph(value = User.GRAPH_FIND_WITH_MEALS, type = EntityGraph.EntityGraphType.FETCH)
    Optional<User> getByIdOrderByMealsDateTimeDesc(Integer id);

    @Override
    List<User> findAll(Sort sort);

    User getByEmail(String email);

//    @Query("select distinct u from User u left join fetch u.meals m where u.id=:id order by m.dateTime desc")
//    User getUserWithMeals(@Param("id") int id);
}
