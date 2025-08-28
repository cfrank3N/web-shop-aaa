package backend2.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class Product {
    @Id
    private int id;
    private String title;
    private double price;
    @Column(length = 1000)
    private String description;
    private String category;
    private String image;
    @Embedded
    private Rating rating;
}
