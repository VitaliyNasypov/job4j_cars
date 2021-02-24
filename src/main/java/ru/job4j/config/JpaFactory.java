package ru.job4j.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaFactory implements ConnectionFactory<EntityManager> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaFactory.class.getName());
    private static EntityManagerFactory entityManagerFactory;

    private JpaFactory() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("ORM");
        } catch (Throwable throwable) {
            LOGGER.error(throwable.getMessage(), throwable);
            throw new ExceptionInInitializerError(throwable);
        }
    }

    private static final class LazyJpa {
        private static final ConnectionFactory<EntityManager> INST = new JpaFactory();
    }

    public static ConnectionFactory<EntityManager> of() {
        return LazyJpa.INST;
    }

    @Override
    public EntityManager getConnection() {
        return entityManagerFactory.createEntityManager();
    }
}
