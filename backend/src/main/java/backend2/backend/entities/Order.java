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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appuser_id", nullable = false)
    private AppUser appUser;

    @ElementCollection
    private Map<Integer, Integer> productIdAndQty;

    @CreationTimestamp
    private Timestamp createdAt;

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public void setProductIdAndQty(Map<Integer, Integer> productIdAndQty) {
        this.productIdAndQty = productIdAndQty;
    }

    public Order() {
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
