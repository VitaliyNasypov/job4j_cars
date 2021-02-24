package ru.job4j.controller;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServletTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServletTest.class.getName());

    @Test
    public void doPost() {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(req.getParameter("signup-name")).thenReturn("name-test-sign-up");
        Mockito.when(req.getParameter("signup-phone")).thenReturn("name-test-sign-up");
        Mockito.when(req.getParameter("signup-email")).thenReturn("name-test-sign-up@test.ru");
        Mockito.when(req.getParameter("signup-password")).thenReturn("name-test-sign-up");
        Mockito.when(req.getRequestDispatcher("index.jsp")).thenReturn(dispatcher);
        try {
            new SignUpServlet().doPost(req, resp);
            Mockito.verify(dispatcher).forward(req, resp);
        } catch (IOException | ServletException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
