package ru.job4j.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "RUDDERS")
public class Rudder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "rudder", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Car> cars;

    public Rudder() {
    }

    public void addCar(Car car) {
        if (cars == null) {
            cars = new HashSet<>();
        }
        cars.add(car);
        car.setRudder(this);
    }

    public void removeCar(Car car) {
        if (cars != null) {
            cars.remove(car);
            car.setRudder(null);
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
        Rudder rudder = (Rudder) o;
        return id == rudder.id
                && Objects.equals(name, rudder.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
