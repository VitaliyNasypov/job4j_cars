package ru.job4j.controller;

import ru.job4j.model.Ad;
import ru.job4j.model.Photo;
import ru.job4j.model.User;
import ru.job4j.service.JpaServiceGeneric;
import ru.job4j.service.ServiceGeneric;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class UploadServlet extends HttpServlet {

    private static final String SAVE_DIR = "images";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String appPath = req.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + SAVE_DIR;
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute("user");
        ServiceGeneric<Photo> serviceGeneric = new JpaServiceGeneric<>(Photo.class);
        Ad ad = (Ad) req.getAttribute("ad");
        List<Photo> photoList = new ArrayList<>();
        for (Part part : req.getParts()) {
            String fileName = part.getSubmittedFileName();
            if (fileName != null && !"".equals(fileName)) {
                part.write(savePath + File.separator + user.getId() + "_" + fileName);
                Photo photo = new Photo();
                photo.setName(user.getId() + "_" + fileName);
                photo.setAd(ad);
                photoList.add(photo);
            }
        }
        serviceGeneric.add(photoList.toArray(new Photo[0]));
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
