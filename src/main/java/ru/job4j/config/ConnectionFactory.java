package ru.job4j.config;

public interface ConnectionFactory<T> {
    T getConnection();
}
