package ru.job4j.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.model.User;

public class JpaServiceUserTest {
    @Test
    public void shouldSuccessfullyAddUser() {
        ServiceUser serviceUser = new JpaServiceUser();
        User user = new User();
        user.setEmail("test-one-user@test.ru");
        user.setPassword("test-one-user");
        user.setName("test-one-user");
        user.setPhone("+71234567890");
        serviceUser.add(user);
        Assertions.assertTrue(user.getId() > 0);
    }

    @Test
    public void shouldMustFindUser() {
        ServiceUser serviceUser = new JpaServiceUser();
        User user = new User();
        user.setEmail("test-two-user@test.ru");
        user.setPassword("test-two-user");
        user.setName("test-two-user");
        user.setPhone("+71234567890");
        serviceUser.add(user);
        Assertions.assertTrue(serviceUser.isUserCreated("test-two-user@test.ru"));
    }

    @Test
    public void shouldAuthenticateUser() {
        ServiceUser serviceUser = new JpaServiceUser();
        User user = new User();
        user.setEmail("test-three-user@test.ru");
        user.setPassword("test-three-user");
        user.setName("test-three-user");
        user.setPhone("+71234567890");
        serviceUser.add(user);
        user.setEmail("test-three-user@test.ru");
        user.setPassword("test-three-user");
        Assertions.assertEquals(serviceUser.findUser(user).getId(), user.getId());
        Assertions.assertEquals(serviceUser.findUser(user).getName(), "test-three-user");
    }

}
