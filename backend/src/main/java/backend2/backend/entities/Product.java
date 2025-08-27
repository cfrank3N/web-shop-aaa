package backend2.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    public int id;
    public String title;
    public double price;
    @Column(length = 1000)
    public String description;
    public String category;
    public String image;
    @Embedded
    public Rating rating;
}
