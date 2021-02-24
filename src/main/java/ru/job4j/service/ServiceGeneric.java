package ru.job4j.service;

import java.util.List;
import java.util.Optional;

public interface ServiceGeneric<T> {
    List<T> add(T... entity);

    Optional<T> get(long id);

    void update(T entity);

    List<T> findAll();
}
