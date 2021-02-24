package ru.job4j.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "MODELS")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;
    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Car> cars;

    public Model() {
    }

    public void addCar(Car car) {
        if (cars == null) {
            cars = new HashSet<>();
        }
        cars.add(car);
        car.setModel(this);
    }

    public void removeCar(Car car) {
        if (cars != null) {
            cars.remove(car);
            car.setModel(null);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Model model = (Model) o;
        return id == model.id && Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
