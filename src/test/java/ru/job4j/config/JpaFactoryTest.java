package ru.job4j.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

public class JpaFactoryTest {

    @Test
    public void shouldOpenConnectionToDatabase() {
        ConnectionFactory<EntityManager> connectionFactory = JpaFactory.of();
        EntityManager entityManager = connectionFactory.getConnection();
        Assertions.assertTrue(entityManager.isOpen());
    }
}
