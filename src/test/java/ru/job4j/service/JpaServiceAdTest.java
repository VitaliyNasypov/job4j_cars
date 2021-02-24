package ru.job4j.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dao.JpaGenericDao;
import ru.job4j.model.Ad;
import ru.job4j.model.Photo;
import ru.job4j.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JpaServiceAdTest {

    private static User user;

    @BeforeAll
    public static void initUser() {
        ServiceUser serviceUser = new JpaServiceUser();
        user = new User();
        user.setEmail("test-ad@test.ru");
        user.setPassword("test-ad");
        user.setName("test-ad");
        user.setPhone("+71234567890");
        user = serviceUser.add(user);
    }

    @Test
    public void shouldAddNewAd() {
        ServiceAd serviceAd = new JpaServiceAd();
        Map<String, String> paramAd = new HashMap<>();
        paramAd.put("price", "700000");
        paramAd.put("color", "Red");
        paramAd.put("mileage", "2342");
        paramAd.put("year", "2005");
        paramAd.put("Engine", "1_Gasoline");
        paramAd.put("Brand", "1_Lada");
        paramAd.put("Model", "1_Vesta");
        paramAd.put("Body", "1_Sedan");
        paramAd.put("Rudder", "1_Left");
        paramAd.put("Transmission", "1_Automatic");
        paramAd.put("User", String.valueOf(user.getId()));
        Ad ad = serviceAd.add(paramAd);
        Assertions.assertTrue(ad.getId() > 0);
    }

    @Test
    public void shouldFindAd() {
        ServiceAd serviceAd = new JpaServiceAd();
        Map<String, String> paramAd = new HashMap<>();
        paramAd.put("price", "700000");
        paramAd.put("color", "Red");
        paramAd.put("mileage", "2342");
        paramAd.put("year", "2005");
        paramAd.put("Engine", "1_Gasoline");
        paramAd.put("Brand", "1_Lada");
        paramAd.put("Model", "1_Vesta");
        paramAd.put("Body", "1_Sedan");
        paramAd.put("Rudder", "1_Left");
        paramAd.put("Transmission", "1_Automatic");
        paramAd.put("User", String.valueOf(user.getId()));
        Ad ad = serviceAd.add(paramAd);
        ServiceGeneric<Photo> serviceGeneric = new JpaServiceGeneric<>(Photo.class);
        List<Photo> photoList = new ArrayList<>();
        for (int y = 0; y < 5; y++) {
            Photo photo = new Photo();
            photo.setAd(ad);
            photo.setName(y + "_name");
            photoList.add(photo);
        }
        serviceGeneric.add(photoList.toArray(new Photo[0]));
        Ad findAd = serviceAd.read(ad.getId()).get();
        Assertions.assertEquals(ad.getId(), findAd.getId());
    }

    @Test
    public void shouldUpdateAd() {
        ServiceAd serviceAd = new JpaServiceAd();
        Map<String, String> paramAd = new HashMap<>();
        paramAd.put("price", "700000");
        paramAd.put("color", "Red");
        paramAd.put("mileage", "2342");
        paramAd.put("year", "2005");
        paramAd.put("Engine", "1_Gasoline");
        paramAd.put("Brand", "1_Lada");
        paramAd.put("Model", "1_Vesta");
        paramAd.put("Body", "1_Sedan");
        paramAd.put("Rudder", "1_Left");
        paramAd.put("Transmission", "1_Automatic");
        paramAd.put("User", String.valueOf(user.getId()));
        Ad ad = serviceAd.add(paramAd);
        Ad adUpdate = new JpaGenericDao<Ad>(Ad.class).read(ad.getId()).get();
        adUpdate.setStatus("Sold");
        serviceAd.update(adUpdate);
        Ad adFind = serviceAd.read(ad.getId()).get();
    }

    @Test
    public void shouldFindKeyAd() {
        Map<String, String> paramAd = new HashMap<>();
        paramAd.put("price", "700000");
        paramAd.put("color", "Red");
        paramAd.put("mileage", "2342");
        paramAd.put("year", "2005");
        paramAd.put("Engine", "1_Gasoline");
        paramAd.put("Brand", "1_Lada");
        paramAd.put("Model", "1_Vesta");
        paramAd.put("Body", "1_Sedan");
        paramAd.put("Rudder", "1_Left");
        paramAd.put("Transmission", "1_Automatic");
        paramAd.put("User", String.valueOf(user.getId()));
        ServiceAd serviceAd = new JpaServiceAd();
        Ad ad = serviceAd.add(paramAd);
        List<Ad> list = serviceAd.findWhereKey("id", String.valueOf(user.getId()));
        int id = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == ad.getId()) {
                id = i;
            }
        }
        Assertions.assertEquals("Lada", list.get(id).getCar().getModel().getBrand().getName());
        Assertions.assertEquals("Gasoline", list.get(id).getCar().getEngine().getType());
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    public void shoudlFindAllLimitAd() {
        ServiceAd serviceAd = new JpaServiceAd();
        for (int i = 0; i < 20; i++) {
            Map<String, String> paramAd = new HashMap<>();
            paramAd.put("price", "700000");
            paramAd.put("color", "Red");
            paramAd.put("mileage", "2342");
            paramAd.put("year", "2005");
            paramAd.put("Engine", "1_Gasoline");
            paramAd.put("Brand", "1_Lada");
            paramAd.put("Model", "1_Vesta");
            paramAd.put("Body", "1_Sedan");
            paramAd.put("Rudder", "1_Left");
            paramAd.put("Transmission", "1_Automatic");
            paramAd.put("User", String.valueOf(user.getId()));
            Ad ad = serviceAd.add(paramAd);
            if (i < 7) {
                ServiceGeneric<Photo> serviceGeneric = new JpaServiceGeneric<>(Photo.class);
                List<Photo> photoList = new ArrayList<>();
                for (int y = 0; y < 5; y++) {
                    Photo photo = new Photo();
                    photo.setAd(ad);
                    photo.setName(y + "_name");
                    photoList.add(photo);
                }
                serviceGeneric.add(photoList.toArray(new Photo[0]));
            }
        }
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("date", "anytime");
        stringMap.put("brand", "Lada");
        stringMap.put("photo", "true");
        List<Ad> list = serviceAd.findAllLimit(7, 6, stringMap);
        Assertions.assertTrue(list.size() > 0 && list.size() <= 6);
        Assertions.assertEquals(2, list.size());
        stringMap.put("photo", "false");
        list = serviceAd.findAllLimit(1, 6, stringMap);
        Assertions.assertTrue(list.size() > 0 && list.size() <= 6);
        Assertions.assertEquals(6, list.size());
    }
}
