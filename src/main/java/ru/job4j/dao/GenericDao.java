package ru.job4j.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenericDao<T> {

    List<T> create(T... entity);

    Optional<T> read(long id);

    void update(T entity);

    void delete(T entity);

    List<T> findAll();

    List<T> findWhereKey(String nameKey, String key);

    List<T> findAllLimit(long startPosition, int maxRow, Map<String, String> param);
}
