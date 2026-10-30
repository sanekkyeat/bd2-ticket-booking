package ro.ugal.ticketbooking.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ticket_code", nullable = false, unique = true, length = 36)
    private String ticketCode;

    @Column(name = "booking_id", nullable = false)
    private Integer bookingId;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;

    @Column(name = "issued_at", insertable = false, updatable = false)
    private OffsetDateTime issuedAt;

    public Ticket() {}

    public Ticket(String ticketCode, Integer bookingId, Integer categoryId, Boolean isUsed) {
        this.ticketCode = ticketCode;
        this.bookingId = bookingId;
        this.categoryId = categoryId;
        this.isUsed = isUsed;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTicketCode() { return ticketCode; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }

    public Integer getBookingId() { return bookingId; }
    public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public Boolean getIsUsed() { return isUsed; }
    public void setIsUsed(Boolean isUsed) { this.isUsed = isUsed; }

    public OffsetDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(OffsetDateTime issuedAt) { this.issuedAt = issuedAt; }
}