package ru.javawebinar.topjava.dao;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractDAOInMemory<T> implements DAO<T> {

    abstract Map<Long, T> getTMap();
    abstract Long getId(T element);
    abstract T setId(T element);

    @Override
    public T merge(T element) {
        if (getId(element) == null) {
            element = setId(element);
        }
        getTMap().put(getId(element), element);
        return element;
    }

    @Override
    public void delete(long id) {
        getTMap().remove(id);
    }

    @Override
    public List<T> findAll() {
        return new LinkedList<>(getTMap().values());
    }

    @Override
    public T findById(long id) {
        return getTMap().get(id);
    }
}
