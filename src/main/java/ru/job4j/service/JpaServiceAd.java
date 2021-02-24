package ru.job4j.service;

import ru.job4j.dao.GenericDao;
import ru.job4j.dao.JpaAdDao;
import ru.job4j.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JpaServiceAd implements ServiceAd {
    private final GenericDao<Ad> genericDao;

    public JpaServiceAd() {
        genericDao = new JpaAdDao<>(Ad.class);
    }

    @Override
    public Optional<Ad> read(long id) {
        return genericDao.read(id);
    }

    @Override
    public void update(Ad ad) {
        genericDao.update(ad);
    }

    @Override
    public Ad add(Map<String, String> paramAd) {
        String[] paramBody = paramAd.get("Body").split("_");
        Body body = new Body();
        body.setId(Long.parseLong(paramBody[0]));
        body.setName(paramBody[1]);
        String[] paramEngine = paramAd.get("Engine").split("_");
        Engine engine = new Engine();
        engine.setId(Long.parseLong(paramEngine[0]));
        engine.setType(paramEngine[1]);
        String[] paramRudder = paramAd.get("Rudder").split("_");
        Rudder rudder = new Rudder();
        rudder.setId(Long.parseLong(paramRudder[0]));
        rudder.setName(paramRudder[1]);
        String[] paramTransmission = paramAd.get("Transmission").split("_");
        Transmission transmission = new Transmission();
        transmission.setId(Long.parseLong(paramTransmission[0]));
        transmission.setName(paramTransmission[1]);
        String[] paramBrand = paramAd.get("Brand").split("_");
        Brand brand = new Brand();
        brand.setId(Long.parseLong(paramBrand[0]));
        brand.setName(paramBrand[1]);
        String[] paramModel = paramAd.get("Model").split("_");
        Model model = new Model();
        model.setId(Long.parseLong(paramModel[0]));
        model.setName(paramModel[1]);
        model.setBrand(brand);
        brand.addModel(model);
        Car car = new Car();
        car.setColor(paramAd.get("color"));
        car.setMileage(Integer.parseInt(paramAd.get("mileage")));
        car.setYear(Integer.parseInt(paramAd.get("year")));
        car.setBody(body);
        body.addCar(car);
        car.setEngine(engine);
        engine.addCar(car);
        car.setRudder(rudder);
        rudder.addCar(car);
        car.setTransmission(transmission);
        transmission.addCar(car);
        car.setModel(model);
        model.addCar(car);
        Ad ad = new Ad();
        ad.setPrice(Integer.parseInt(paramAd.get("price")));
        ad.setStatus("For sale");
        ad.setCreatedAd(LocalDateTime.now());
        ad.setCar(car);
        User user = new User();
        user.setId(Long.parseLong(paramAd.get("User")));
        ad.setUser(user);
        genericDao.create(ad);
        return ad;
    }

    @Override
    public List<Ad> findWhereKey(String nameKey, String valueKey) {
        return genericDao.findWhereKey(nameKey, valueKey);
    }

    @Override
    public List<Ad> findAllLimit(long startPosition, int maxRow, Map<String, String> param) {
        param.put("photo", Boolean.parseBoolean(param.get("photo")) ? "AND p.id IS NOT NULL" : "");
        param.put("left join", Boolean.parseBoolean(param.get("photo")) ? "" : " left");
        param.put("brand", param.get("brand").equals("All") ? "%" : param.get("brand"));
        param.put("date", param.get("date").equals("anytime")
                ? LocalDateTime.of(LocalDate.now().minusYears(50), LocalTime.MIDNIGHT).toString()
                : LocalDate.now().atStartOfDay().toString());
        return genericDao.findAllLimit(startPosition, maxRow, param);
    }
}
