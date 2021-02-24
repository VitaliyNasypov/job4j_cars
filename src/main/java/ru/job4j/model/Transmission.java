package ru.job4j.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TRANSMISSIONS")
public class Transmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "transmission", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Car> cars;

    public Transmission() {
    }

    public void addCar(Car car) {
        if (cars == null) {
            cars = new HashSet<>();
        }
        cars.add(car);
        car.setTransmission(this);
    }

    public void removeCar(Car car) {
        if (cars != null) {
            cars.remove(car);
            car.setTransmission(null);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transmission that = (Transmission) o;
        return id == that.id
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
