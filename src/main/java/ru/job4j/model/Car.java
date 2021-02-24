package ru.job4j.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CARS")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "color", nullable = false)
    private String color;
    @Column(name = "mileage", nullable = false)
    private int mileage;
    @Column(name = "year", nullable = false)
    private int year;
    @ManyToOne(fetch = FetchType.LAZY)
    private Engine engine;
    @ManyToOne(fetch = FetchType.LAZY)
    private Model model;
    @ManyToOne(fetch = FetchType.LAZY)
    private Rudder rudder;
    @ManyToOne(fetch = FetchType.LAZY)
    private Transmission transmission;
    @ManyToOne(fetch = FetchType.LAZY)
    private Body body;

    public Car() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Rudder getRudder() {
        return rudder;
    }

    public void setRudder(Rudder rudder) {
        this.rudder = rudder;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id
                && mileage == car.mileage
                && year == car.year
                && Objects.equals(color, car.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color, mileage, year);
    }
}
