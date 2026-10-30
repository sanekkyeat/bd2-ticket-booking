package ro.ugal.ticketbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ugal.ticketbooking.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {
}