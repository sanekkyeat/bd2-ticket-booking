package ro.ugal.ticketbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ugal.ticketbooking.entity.TicketCategory;

import java.util.List;

@Repository
public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Integer> {
    List<TicketCategory> findByEventId(Integer eventId);
}