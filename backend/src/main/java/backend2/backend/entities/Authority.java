package backend2.backend.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Authority {

    @Id
    @GeneratedValue
    private int id;
    @ManyToMany(mappedBy = "authorities")
    private List<AppUser> appUsers;
    private String authority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<AppUser> getCustomers() {
        return appUsers;
    }

    public void setCustomers(List<AppUser> appUser) {
        this.appUsers = appUser;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
