package ro.ugal.ticketbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ugal.ticketbooking.entity.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByStatus(String status);
    List<Event> findByVenueId(Integer venueId);
}