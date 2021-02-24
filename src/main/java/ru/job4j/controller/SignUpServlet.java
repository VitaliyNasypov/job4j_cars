package ru.job4j.controller;

import ru.job4j.model.User;
import ru.job4j.service.JpaServiceUser;
import ru.job4j.service.ServiceUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setName(req.getParameter("signup-name"));
        user.setPhone(req.getParameter("signup-phone"));
        user.setEmail(req.getParameter("signup-email"));
        user.setPassword(req.getParameter("signup-password"));
        ServiceUser serviceUser = new JpaServiceUser();
        if (serviceUser.isUserCreated(user.getEmail())) {
            req.setAttribute("error", "User exists");
        } else {
            serviceUser.add(user);
            req.setAttribute("info", "Registration was successful! Please login to your account.");
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
