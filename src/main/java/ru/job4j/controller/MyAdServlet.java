package ru.job4j.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.job4j.model.Ad;
import ru.job4j.model.Photo;
import ru.job4j.model.User;
import ru.job4j.service.JpaServiceAd;
import ru.job4j.service.JpaServiceGeneric;
import ru.job4j.service.ServiceAd;
import ru.job4j.service.ServiceGeneric;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/my-ad")
public class MyAdServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        ServiceAd serviceAd = new JpaServiceAd();
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute("user");
        List<Ad> list = serviceAd.findWhereKey("u.id", String.valueOf(user.getId()));
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Ad ad : list) {
            List<String> paramResponse = new ArrayList<>();
            paramResponse.add(String.valueOf(ad.getId()));
            paramResponse.add(String.valueOf(ad.getPrice()));
            paramResponse.add(ad.getStatus());
            paramResponse.add(ad.getCar().getModel().getBrand().getName());
            paramResponse.add(ad.getCar().getModel().getName());
            paramResponse.add(ad.getPhotos().stream().findFirst().orElse(new Photo()).getName());
            jsonArray.put(paramResponse);
        }
        jsonObject.put("ads", jsonArray);
        resp.getWriter().print(jsonObject);
        resp.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        BufferedReader br = req.getReader();
        JSONObject jsonObject = new JSONObject(br.readLine());
        ServiceGeneric<Ad> serviceGeneric = new JpaServiceGeneric<>(Ad.class);
        Ad ad = serviceGeneric.get(jsonObject.getLong("id")).orElse(new Ad());
        ad.setStatus(jsonObject.getString("status"));
        serviceGeneric.update(ad);
        resp.getWriter().print("200");
        resp.getWriter().flush();
    }
}
