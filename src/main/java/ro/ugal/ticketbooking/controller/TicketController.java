package ro.ugal.ticketbooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ugal.ticketbooking.entity.Ticket;
import ro.ugal.ticketbooking.repository.TicketRepository;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketRepository ticketRepository;

    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping
    public List<Ticket> getAllTickets(@RequestParam(required = false) Integer bookingId) {
        if (bookingId != null) {
            return ticketRepository.findByBookingId(bookingId);
        }
        return ticketRepository.findAll();
    }

    @GetMapping("/verify/{ticketCode}")
    public ResponseEntity<Ticket> verifyTicket(@PathVariable String ticketCode) {
        return ticketRepository.findByTicketCode(ticketCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/validate/{ticketCode}")
    public ResponseEntity<Ticket> markTicketAsUsed(@PathVariable String ticketCode) {
        return ticketRepository.findByTicketCode(ticketCode).map(ticket -> {
            ticket.setIsUsed(true);
            return ResponseEntity.ok(ticketRepository.save(ticket));
        }).orElse(ResponseEntity.notFound().build());
    }
}