package backend2.backend.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Rating {
    public double rate;
    public int count;
}
