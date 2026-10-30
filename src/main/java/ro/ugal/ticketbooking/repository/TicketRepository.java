package ro.ugal.ticketbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ugal.ticketbooking.entity.Ticket;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByBookingId(Integer bookingId);
    Optional<Ticket> findByTicketCode(String ticketCode);
}