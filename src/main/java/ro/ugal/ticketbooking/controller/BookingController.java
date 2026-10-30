package ro.ugal.ticketbooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ugal.ticketbooking.entity.Booking;
import ro.ugal.ticketbooking.repository.BookingRepository;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;

    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @GetMapping
    public List<Booking> getAllBookings(@RequestParam(required = false) Integer userId) {
        if (userId != null) {
            return bookingRepository.findByUserId(userId);
        }
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Integer id) {
        return bookingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking saved = bookingRepository.save(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Integer id) {
        return bookingRepository.findById(id).map(b -> {
            b.setStatus("CANCELLED");
            return ResponseEntity.ok(bookingRepository.save(b));
        }).orElse(ResponseEntity.notFound().build());
    }
}