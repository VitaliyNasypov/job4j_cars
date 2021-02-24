package ru.job4j.controller;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServletTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServletTest.class.getName());

    @Test
    public void doGet() {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        User user = new User();
        session.setAttribute("user", user);
        Mockito.when(req.getParameter("out")).thenReturn("true");
        Mockito.when(req.getSession()).thenReturn(session);
        try {
            new LoginServlet().doGet(req, resp);
            Mockito.verify(session).removeAttribute("user");
            Mockito.verify(resp).sendRedirect(req.getContextPath() + "/");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Test
    public void doPost() {
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
        HttpSession httpSession = Mockito.mock(HttpSession.class);
        RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(req.getSession()).thenReturn(httpSession);
        Mockito.when(req.getParameter("login-email")).thenReturn("fail@test.ru");
        Mockito.when(req.getParameter("login-password")).thenReturn("test");
        Mockito.when(req.getRequestDispatcher("index.jsp")).thenReturn(dispatcher);
        try {
            new LoginServlet().doPost(req, resp);
            Mockito.verify(dispatcher).forward(req, resp);
        } catch (IOException | ServletException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
