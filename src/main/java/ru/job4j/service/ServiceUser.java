package ru.job4j.service;

import ru.job4j.model.User;

public interface ServiceUser {
    User add(User user);

    boolean isUserCreated(String email);

    User findUser(User user);
}
