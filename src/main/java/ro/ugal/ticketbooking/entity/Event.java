package ro.ugal.ticketbooking.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 120)
    private String artist;

    @Column(length = 50)
    private String genre;

    @Column(name = "event_date", nullable = false)
    private OffsetDateTime eventDate;

    @Column(name = "venue_id", nullable = false)
    private Integer venueId;

    @Column(name = "organizer_id", nullable = false)
    private Integer organizerId;

    @Column(nullable = false, length = 20)
    private String status = "SCHEDULED";

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    public Event() {}

    public Event(String title, String artist, String genre, OffsetDateTime eventDate, Integer venueId, Integer organizerId, String status) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.eventDate = eventDate;
        this.venueId = venueId;
        this.organizerId = organizerId;
        this.status = status;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public OffsetDateTime getEventDate() { return eventDate; }
    public void setEventDate(OffsetDateTime eventDate) { this.eventDate = eventDate; }

    public Integer getVenueId() { return venueId; }
    public void setVenueId(Integer venueId) { this.venueId = venueId; }

    public Integer getOrganizerId() { return organizerId; }
    public void setOrganizerId(Integer organizerId) { this.organizerId = organizerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}