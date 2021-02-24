package ru.job4j.service;

import ru.job4j.model.Ad;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ServiceAd {
    Ad add(Map<String, String> paramAd);

    Optional<Ad> read(long id);

    void update(Ad ad);

    List<Ad> findWhereKey(String nameKey, String valueKey);

    List<Ad> findAllLimit(long startPosition, int maxRow, Map<String, String> param);

}
