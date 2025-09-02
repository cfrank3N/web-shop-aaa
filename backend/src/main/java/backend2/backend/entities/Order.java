package backend2.backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appuser_id", nullable = false)
    private AppUser appUserId;

    @ElementCollection
    private List<Integer> orderedProductIds;

    @CreationTimestamp
    private Timestamp createdAt;

}
