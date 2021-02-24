package ru.job4j.model;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ADS")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Car car;
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Photo> photos;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "create_ad", nullable = false)
    private LocalDateTime createdAd;
    @Column(name = "status", nullable = false)
    private String status;

    public Ad() {
    }

    public void addPhoto(Photo photo) {
        if (photos == null) {
            photos = new HashSet<>();
        }
        photos.add(photo);
        photo.setAd(this);
    }

    public void removePhoto(Photo photo) {
        if (photos != null) {
            photos.remove(photo);
            photo.setAd(null);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAd() {
        return createdAd;
    }

    public void setCreatedAd(LocalDateTime createdAd) {
        this.createdAd = createdAd;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ad ad = (Ad) o;
        return id == ad.id
                && price == ad.price
                && Objects.equals(createdAd, ad.createdAd)
                && Objects.equals(status, ad.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, createdAd, status);
    }
}
