package ru.job4j.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "BRANDS")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Model> models;

    public Brand() {
    }

    public void addModel(Model model) {
        if (models == null) {
            models = new HashSet<>();
        }
        models.add(model);
        model.setBrand(this);
    }

    public void removeModel(Model model) {
        if (models != null) {
            models.remove(model);
            model.setBrand(null);
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
        Brand brand = (Brand) o;
        return id == brand.id && Objects.equals(name, brand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
