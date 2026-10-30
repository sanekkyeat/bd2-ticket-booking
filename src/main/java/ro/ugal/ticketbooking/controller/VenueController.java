package ro.ugal.ticketbooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ugal.ticketbooking.entity.Venue;
import ro.ugal.ticketbooking.repository.VenueRepository;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueRepository venueRepository;

    public VenueController(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @GetMapping
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable Integer id) {
        return venueRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
        Venue savedVenue = venueRepository.save(venue);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVenue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venue> updateVenue(@PathVariable Integer id, @RequestBody Venue updatedVenue) {
        return venueRepository.findById(id).map(venue -> {
            venue.setName(updatedVenue.getName());
            venue.setCity(updatedVenue.getCity());
            venue.setAddress(updatedVenue.getAddress());
            venue.setTotalCapacity(updatedVenue.getTotalCapacity());
            return ResponseEntity.ok(venueRepository.save(venue));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Integer id) {
        if (!venueRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        venueRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}