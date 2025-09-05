package backend2.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
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
