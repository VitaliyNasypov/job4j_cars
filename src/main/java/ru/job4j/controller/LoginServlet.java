package ru.job4j.controller;

import ru.job4j.model.User;
import ru.job4j.service.JpaServiceUser;
import ru.job4j.service.ServiceUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        if (req.getParameter("out") != null) {
            HttpSession session = req.getSession();
            session.removeAttribute("user");
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setEmail(req.getParameter("login-email"));
        user.setPassword(req.getParameter("login-password"));
        ServiceUser serviceUser = new JpaServiceUser();
        user = serviceUser.findUser(user);
        if (user.getId() > 0) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("error", "Invalid Email or Password!");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}
