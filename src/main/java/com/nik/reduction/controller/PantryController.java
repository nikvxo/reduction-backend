package com.nik.reduction.controller;

import com.nik.reduction.dto.PantryItemRequest;
import com.nik.reduction.model.PantryItem;
import com.nik.reduction.service.PantryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pantry")
public class PantryController {

    private final PantryService pantryService;

    public PantryController(PantryService pantryService) {
        this.pantryService = pantryService;
    }

    @GetMapping
    public List<PantryItem> getAllItems() {
        return pantryService.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PantryItem> getItemById(@PathVariable Long id) {
        return pantryService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PantryItem> createItem(@RequestBody PantryItemRequest request) {
        PantryItem created = pantryService.createItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PantryItem> updateItem(
            @PathVariable Long id,
            @RequestBody PantryItemRequest request) {
        return pantryService.updateItem(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        pantryService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/expiring")
    public List<PantryItem> getExpiringItems(
            @RequestParam(defaultValue = "7") int days) {
        return pantryService.getExpiringItems(days);
    }

    @GetMapping("/expired")
    public List<PantryItem> getExpiredItems() {
        return pantryService.getExpiredItems();
    }
}
