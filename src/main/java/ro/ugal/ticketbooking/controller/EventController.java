package ro.ugal.ticketbooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ugal.ticketbooking.entity.Event;
import ro.ugal.ticketbooking.repository.EventRepository;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // READ ALL
    @GetMapping
    public List<Event> getAllEvents(@RequestParam(required = false) String status) {
        if (status != null && !status.isBlank()) {
            return eventRepository.findByStatus(status.toUpperCase());
        }
        return eventRepository.findAll();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Integer id) {
        return eventRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event saved = eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Integer id, @RequestBody Event updated) {
        return eventRepository.findById(id).map(event -> {
            event.setTitle(updated.getTitle());
            event.setArtist(updated.getArtist());
            event.setGenre(updated.getGenre());
            event.setEventDate(updated.getEventDate());
            event.setStatus(updated.getStatus());
            event.setVenueId(updated.getVenueId());
            event.setOrganizerId(updated.getOrganizerId());
            return ResponseEntity.ok(eventRepository.save(event));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        if (!eventRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        eventRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}