package ru.job4j.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.model.Brand;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class JpaGenericDaoTest {

    @Test
    public void shouldDeleteEntity() {
        Brand brand = new Brand();
        brand.setName("BMW");
        GenericDao<Brand> genericDao = new JpaGenericDao<>(Brand.class);
        genericDao.create(brand);
        Assertions.assertEquals(genericDao.read(brand.getId()).get(), brand);
        genericDao.delete(brand);
        Assertions.assertThrows(NoSuchElementException.class,
                () -> genericDao.read(brand.getId()).get());
    }

    @Test
    public void shouldFindAllLimitEntity() {
        Brand brand = new Brand();
        brand.setName("BMW");
        GenericDao<Brand> genericDao = new JpaGenericDao<>(Brand.class);
        List<Brand> brandList = genericDao.findAllLimit(0L, 2, new HashMap<>());
        Assertions.assertEquals(brandList.size(), 2);
    }
}
