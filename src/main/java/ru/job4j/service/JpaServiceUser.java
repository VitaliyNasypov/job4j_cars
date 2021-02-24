package ru.job4j.service;

import ru.job4j.dao.GenericDao;
import ru.job4j.dao.JpaGenericDao;
import ru.job4j.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class JpaServiceUser implements ServiceUser {
    private GenericDao<User> genericDao;

    public JpaServiceUser() {
        genericDao = new JpaGenericDao<>(User.class);
    }

    public User add(User user) {
        user.setPassword(new PasswordHashUser()
                .generatePasswordHash(user.getPassword(), user.getEmail(),
                        10000, 512, "PBKDF2WithHmacSHA512"));
        return genericDao.create(user).stream().peek(e -> {
            user.setPassword("");
            user.setEmail("");
        }).collect(Collectors.toList()).get(0);
    }

    public boolean isUserCreated(String email) {
        return genericDao.findWhereKey("email", email).size() == 1;
    }

    public User findUser(User user) {
        List<User> list = genericDao.findWhereKey("email", user.getEmail());
        if (list.size() == 1 && new PasswordHashUser().validatePassword(user.getPassword(),
                list.get(0).getPassword())) {
            list.get(0).setPassword("");
            list.get(0).setEmail("");
            return list.get(0);
        } else {
            return new User();
        }
    }
}
