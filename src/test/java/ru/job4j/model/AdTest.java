package ru.job4j.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class AdTest {

    @Test
    public void shouldEqualsTwoAd() {
        Ad first = new Ad();
        Ad second = new Ad();
        for (int i = 0; i < 2; i++) {
            Car car = new Car();
            car.setYear(2005);
            car.setColor("Red");
            car.setId(1L);
            car.setMileage(45000);
            Body body = new Body();
            body.setName("Sedan");
            body.setId(1L);
            car.setBody(body);
            body.addCar(car);
            Transmission transmission = new Transmission();
            transmission.setId(1L);
            transmission.setName("Automatic");
            transmission.addCar(car);
            car.setTransmission(transmission);
            Rudder rudder = new Rudder();
            rudder.setId(1L);
            rudder.setName("Left");
            rudder.addCar(car);
            car.setRudder(rudder);
            Brand brand = new Brand();
            brand.setId(1L);
            brand.setName("Lada");
            Model model = new Model();
            model.setId(1L);
            model.setName("Vest");
            model.setBrand(brand);
            brand.addModel(model);
            model.addCar(car);
            car.setModel(model);
            Engine engine = new Engine();
            engine.setId(1L);
            engine.setType("Gasoline");
            engine.addCar(car);
            car.setEngine(engine);
            User user = new User();
            user.setId(1L);
            user.setPhone("+7");
            user.setPassword("test");
            user.setEmail("test@test.ru");
            Photo photo = new Photo();
            photo.setName("img.jpg");
            photo.setId(1L);
            Ad ad = new Ad();
            ad.setId(1L);
            ad.setStatus("For sale");
            ad.setPrice(700000);
            ad.setCreatedAd(LocalDateTime.of(2021, 2, 24, 12, 30));
            ad.setUser(user);
            user.addAd(ad);
            ad.setCar(car);
            ad.addPhoto(photo);
            photo.setAd(ad);
            if (i == 0) {
                first = ad;
            } else {
                second = ad;
            }
        }
        Assertions.assertEquals(first, second);
        Assertions.assertEquals(first.getCar(), second.getCar());
        Assertions.assertEquals(first.getCar().getBody(), second.getCar().getBody());
        Assertions.assertEquals(first.getCar().getEngine(), second.getCar().getEngine());
        Assertions.assertEquals(first.getCar().getModel(), second.getCar().getModel());
        Assertions.assertEquals(first.getCar().getModel().getBrand(), second.getCar()
                .getModel().getBrand());
        Assertions.assertEquals(first.getCar().getRudder(), second.getCar().getRudder());
        Assertions.assertEquals(first.getCar().getTransmission(),
                second.getCar().getTransmission());
        Assertions.assertEquals(first.getCar().getRudder(), second.getCar().getRudder());
        Assertions.assertEquals(first.getPhotos(), second.getPhotos());
        Assertions.assertEquals(first.getUser(), second.getUser());

        Assertions.assertEquals(first.getUser().hashCode(), second.getUser().hashCode());
    }
}
