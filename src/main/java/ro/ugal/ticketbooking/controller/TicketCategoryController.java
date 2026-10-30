package ro.ugal.ticketbooking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ugal.ticketbooking.entity.TicketCategory;
import ro.ugal.ticketbooking.repository.TicketCategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class TicketCategoryController {

    private final TicketCategoryRepository categoryRepository;

    public TicketCategoryController(TicketCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<TicketCategory> getAllCategories(@RequestParam(required = false) Integer eventId) {
        if (eventId != null) {
            return categoryRepository.findByEventId(eventId);
        }
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketCategory> getCategoryById(@PathVariable Integer id) {
        return categoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TicketCategory> createCategory(@RequestBody TicketCategory category) {
        TicketCategory saved = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketCategory> updateCategory(@PathVariable Integer id, @RequestBody TicketCategory updated) {
        return categoryRepository.findById(id).map(cat -> {
            cat.setName(updated.getName());
            cat.setPrice(updated.getPrice());
            cat.setTotalQuantity(updated.getTotalQuantity());
            cat.setAvailableQuantity(updated.getAvailableQuantity());
            return ResponseEntity.ok(categoryRepository.save(cat));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}