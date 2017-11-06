package ru.javawebinar.topjava.dao;

import java.util.List;

public interface DAO<T> {
    T merge(T element);
    void delete(long id);
    List<T> findAll();
    T findById(long id);
}
