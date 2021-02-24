package ru.job4j.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "BODIES")
public class Body {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @OneToMany(mappedBy = "body", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Car> cars;

    public Body() {
    }

    public void addCar(Car car) {
        if (cars == null) {
            cars = new HashSet<>();
        }
        cars.add(car);
        car.setBody(this);
    }

    public void removeCar(Car car) {
        if (cars != null) {
            cars.remove(car);
            car.setBody(null);
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
        Body body = (Body) o;
        return id == body.id && Objects.equals(name, body.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
