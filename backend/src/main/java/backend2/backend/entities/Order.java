package backend2.backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Map;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "appuser_id", nullable = false)
    private AppUser appUser;

    @ElementCollection
    private Map<Integer, Integer> productIdAndQty;

    @CreationTimestamp
    private Timestamp createdAt;


    public Order() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }


    public Map<Integer, Integer> getProductIdAndQty() {
        return productIdAndQty;
    }

    public void setProductIdAndQty(Map<Integer, Integer> productIdAndQty) {
        this.productIdAndQty = productIdAndQty;
    }


    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", appUser=" + appUser +
                ", productIdAndQty=" + productIdAndQty +
                '}';
    }
}
