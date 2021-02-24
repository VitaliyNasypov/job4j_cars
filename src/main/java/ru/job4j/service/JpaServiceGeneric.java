package ru.job4j.service;

import ru.job4j.dao.GenericDao;
import ru.job4j.dao.JpaGenericDao;

import java.util.List;
import java.util.Optional;

public class JpaServiceGeneric<T> implements ServiceGeneric<T> {
    private final GenericDao<T> genericDao;

    public JpaServiceGeneric(Class<T> type) {
        genericDao = new JpaGenericDao<>(type);
    }

    @Override
    public Optional<T> get(long id) {
        return genericDao.read(id);
    }

    @Override
    public void update(T entity) {
        genericDao.update(entity);
    }

    @SafeVarargs
    @Override
    public final List<T> add(T... entity) {
        return genericDao.create(entity);
    }

    @Override
    public List<T> findAll() {
        return genericDao.findAll();
    }
}
