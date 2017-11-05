package ru.javawebinar.topjava.dao;

import java.util.List;

public interface CRUD<T> {
    public List<T> findAll();
    public T findById(long id);
    public T update(T entity);
    public T create(T entity);
    public void delete(T entity);
    public void delete(long id);
}
