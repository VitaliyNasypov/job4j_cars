package ru.job4j.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ENGINES")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "type", unique = true, nullable = false)
    private String type;
    @OneToMany(mappedBy = "engine", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Car> cars;

    public Engine() {
    }

    public void addCar(Car car) {
        if (cars == null) {
            cars = new HashSet<>();
        }
        cars.add(car);
        car.setEngine(this);
    }

    public void removeCar(Car car) {
        if (cars != null) {
            cars.remove(car);
            car.setEngine(null);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Engine engine = (Engine) o;
        return id == engine.id && Objects.equals(type, engine.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }
}
