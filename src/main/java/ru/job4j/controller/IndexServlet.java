package ru.job4j.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.job4j.model.Ad;
import ru.job4j.model.Photo;
import ru.job4j.service.JpaServiceAd;
import ru.job4j.service.ServiceAd;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        ServiceAd serviceAd = new JpaServiceAd();
        String id = req.getParameter("id");
        Ad ad = serviceAd.read(Long.parseLong(id)).orElse(new Ad());
        Map<String, String> param = new HashMap<>();
        param.put("Price", String.valueOf(ad.getPrice()));
        param.put("Status", ad.getStatus());
        param.put("User phone", ad.getUser().getPhone());
        param.put("Color", ad.getCar().getColor());
        param.put("Mileage", String.valueOf(ad.getCar().getMileage()));
        param.put("Year", String.valueOf(ad.getCar().getYear()));
        param.put("Engine", ad.getCar().getEngine().getType());
        param.put("Rudder", ad.getCar().getRudder().getName());
        param.put("Transmission", ad.getCar().getTransmission().getName());
        param.put("Body", ad.getCar().getBody().getName());
        param.put("Model", ad.getCar().getModel().getName());
        param.put("Brand", ad.getCar().getModel().getBrand().getName());
        param.put("Phone", ad.getUser().getPhone());
        String photos = "";
        if (ad.getPhotos().size() > 0) {
            for (Photo photo : ad.getPhotos()) {
                photos = photo.getName() + ",";
            }
        }
        param.put("Photo", photos);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ad", param);
        resp.getWriter().print(jsonObject);
        resp.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        BufferedReader br = req.getReader();
        JSONObject jsonObject = new JSONObject(br.readLine());
        Map<String, String> param = new HashMap<>();
        param.put("date", jsonObject.getString("date"));
        param.put("photo", String.valueOf(jsonObject.getBoolean("photo")));
        param.put("brand", jsonObject.getString("brand"));
        ServiceAd serviceAd = new JpaServiceAd();
        List<Ad> adList = serviceAd.findAllLimit(jsonObject.getLong("page"), 6, param);
        jsonObject.remove("date");
        jsonObject.remove("photo");
        jsonObject.remove("brand");
        jsonObject.remove("page");
        JSONArray jsonArray = new JSONArray();
        for (Ad ad : adList) {
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
}
