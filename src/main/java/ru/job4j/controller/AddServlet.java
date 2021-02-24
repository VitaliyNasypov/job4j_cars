package ru.job4j.controller;

import org.json.JSONObject;
import ru.job4j.model.*;
import ru.job4j.service.JpaServiceAd;
import ru.job4j.service.JpaServiceGeneric;
import ru.job4j.service.ServiceAd;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/add")
@MultipartConfig
public class AddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        JSONObject jsonObject = new JSONObject();
        List<Brand> brandList = new JpaServiceGeneric<>(Brand.class).findAll();
        jsonObject.put("Brand", brandList);
        List<Model> modelList = new JpaServiceGeneric<>(Model.class).findAll().stream()
                .peek(e -> {
                    Brand brand = new Brand();
                    brand.setId(e.getBrand().getId());
                    e.setBrand(brand);
                }).collect(Collectors.toList());
        jsonObject.put("Model", modelList);
        List<Body> bodyList = new JpaServiceGeneric<>(Body.class).findAll();
        jsonObject.put("Body", bodyList);
        List<Engine> engineList = new JpaServiceGeneric<>(Engine.class).findAll();
        jsonObject.put("Engine", engineList);
        List<Rudder> rudderList = new JpaServiceGeneric<>(Rudder.class).findAll();
        jsonObject.put("Rudder", rudderList);
        List<Transmission> transmissionList = new JpaServiceGeneric<>(Transmission.class).findAll();
        jsonObject.put("Transmission", transmissionList);
        resp.getWriter().print(jsonObject);
        resp.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Map<String, String> paramAd = new HashMap<>();
        paramAd.put("price", req.getParameter("price"));
        paramAd.put("color", req.getParameter("color"));
        paramAd.put("mileage", req.getParameter("mileage"));
        paramAd.put("year", req.getParameter("year"));
        paramAd.put("Engine", req.getParameter("engine"));
        paramAd.put("Brand", req.getParameter("brand"));
        paramAd.put("Model", req.getParameter("model"));
        paramAd.put("Body", req.getParameter("body"));
        paramAd.put("Rudder", req.getParameter("rudder"));
        paramAd.put("Transmission", req.getParameter("transmission"));
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute("user");
        paramAd.put("User", String.valueOf(user.getId()));
        ServiceAd serviceAd = new JpaServiceAd();
        Ad ad = serviceAd.add(paramAd);
        req.setAttribute("ad", ad);
        getServletContext().getRequestDispatcher("/UploadServlet").forward(
                req, resp);
    }
}
