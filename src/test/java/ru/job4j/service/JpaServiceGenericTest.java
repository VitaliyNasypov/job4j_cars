package ru.job4j.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.model.Ad;
import ru.job4j.model.Engine;
import ru.job4j.model.Photo;
import ru.job4j.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JpaServiceGenericTest {

    private static User user;

    @BeforeAll
    public static void initUser() {
        ServiceUser serviceUser = new JpaServiceUser();
        user = new User();
        user.setEmail("test-generic@test.ru");
        user.setPassword("test-generic");
        user.setName("test-generic");
        user.setPhone("+71234567890");
        user = serviceUser.add(user);
    }

    @Test
    public void shouldSuccessfullyAddEntity() {
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
        for (int i = 0; i < 5; i++) {
            Photo photo = new Photo();
            photo.setAd(ad);
            photo.setName(i + "_name");
            photoList.add(photo);
        }
        photoList = serviceGeneric.add(photoList.toArray(new Photo[0]));
        for (Photo photo : photoList) {
            Assertions.assertTrue(photo.getId() > 0);
        }
    }

    @Test
    public void shouldSuccessfullyGetEntity() {
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
        ServiceGeneric<Ad> serviceGeneric = new JpaServiceGeneric<>(Ad.class);
        Ad adGet = serviceGeneric.get(ad.getId()).get();
        Assertions.assertEquals(adGet.getId(), ad.getId());
        Assertions.assertEquals(adGet.getPrice(), ad.getPrice());
    }

    @Test
    public void shouldSuccessfullyUpdateUser() {
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
        ServiceGeneric<Ad> serviceGeneric = new JpaServiceGeneric<>(Ad.class);
        Ad adGet = serviceGeneric.get(ad.getId()).get();
        adGet.setStatus("Sold");
        serviceGeneric.update(adGet);
        Assertions.assertEquals(serviceGeneric.get(ad.getId()).get().getStatus(), "Sold");
    }

    @Test
    public void shouldSuccessfullyFindAllEntity() {
        ServiceGeneric<Engine> serviceGeneric = new JpaServiceGeneric<>(Engine.class);
        List<Engine> engineList = serviceGeneric.findAll();
        Engine engine = engineList.stream().filter(e -> e.getType()
                .equals("Gasoline")).findFirst().get();
        Assertions.assertEquals(engineList.size(), 3);
        Assertions.assertEquals("Gasoline", engine.getType());
    }
}
