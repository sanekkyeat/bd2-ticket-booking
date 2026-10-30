package ro.ugal.ticketbooking.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "venues")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // заменено с Long на Integer

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 80)
    private String city;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(name = "total_capacity", nullable = false)
    private Integer totalCapacity;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    public Venue() {}

    public Venue(String name, String city, String address, Integer totalCapacity) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.totalCapacity = totalCapacity;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getTotalCapacity() { return totalCapacity; }
    public void setTotalCapacity(Integer totalCapacity) { this.totalCapacity = totalCapacity; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}