package backend2.backend.entities;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Entity
public class Authority {

    @Id
    @GeneratedValue
    private int id;
    @ManyToMany(mappedBy = "authorities")
    private List<Customer> customers;
    private String authority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customer) {
        this.customers = customer;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
