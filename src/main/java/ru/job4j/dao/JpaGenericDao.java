package ru.job4j.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.config.ConnectionFactory;
import ru.job4j.config.JpaFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JpaGenericDao<T> implements GenericDao<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaGenericDao.class.getName());
    private static ConnectionFactory<EntityManager> connectionFactory;
    private final Class<T> type;

    public JpaGenericDao(Class<T> type) {
        connectionFactory = JpaFactory.of();
        this.type = type;
    }

    protected <R> R returnResult(final Function<EntityManager, R> action) {
        final EntityManager entityManager = connectionFactory.getConnection();
        final EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            R result = action.apply(entityManager);
            entityTransaction.commit();
            return result;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            entityTransaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    protected void voidResult(final Consumer<EntityManager> action) {
        final EntityManager entityManager = connectionFactory.getConnection();
        final EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            action.accept(entityManager);
            entityTransaction.commit();
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
            entityTransaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @SafeVarargs
    @Override
    public final List<T> create(T... entity) {
        return returnResult(entityManager ->
                Arrays.stream(entity).peek(entityManager::persist).collect(Collectors.toList())
        );
    }

    @Override
    public Optional<T> read(long id) {
        return Optional.ofNullable(returnResult(entityManager -> entityManager.find(type, id)));
    }

    @Override
    public void update(T entity) {
        voidResult(entityManager -> entityManager.merge(entity));
    }

    @Override
    public void delete(T entity) {
        voidResult(entityManager -> entityManager.remove(entityManager.contains(entity)
                ? entity
                : entityManager.merge(entity)));
    }

    @Override
    public List<T> findAll() {
        return returnResult(entityManager ->
                entityManager.createQuery("from " + type.getName(), type).getResultList());
    }

    @Override
    public List<T> findWhereKey(String nameKey, String valueKey) {
        return returnResult(entityManager ->
                entityManager.createQuery("from " + type.getName() + " where "
                        + nameKey + " = :" + nameKey, type)
                        .setParameter(nameKey, valueKey).getResultList());
    }

    @Override
    public List<T> findAllLimit(long startPosition, int maxRow, Map<String, String> param) {
        return returnResult(entityManager ->
                entityManager.createQuery("from " + type.getName(), type)
                        .setFirstResult((int) startPosition)
                        .setMaxResults(maxRow)
                        .getResultList());
    }
}
