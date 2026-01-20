package com.nik.reduction.service;

import com.nik.reduction.dto.PantryItemRequest;
import com.nik.reduction.model.PantryItem;
import com.nik.reduction.repository.PantryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PantryService {

    private final PantryRepository pantryRepository;

    public PantryService(PantryRepository pantryRepository) {
        this.pantryRepository = pantryRepository;
    }

    public List<PantryItem> getAllItems() {
        return pantryRepository.findAllOrderByExpiration();
    }

    public Optional<PantryItem> getItemById(Long id) {
        return pantryRepository.findById(id);
    }

    @Transactional
    public PantryItem createItem(PantryItemRequest request) {
        PantryItem item = new PantryItem();
        item.setName(request.getName());
        item.setQuantity(request.getQuantity());
        item.setUnit(request.getUnit());
        item.setExpirationDate(request.getExpirationDate());
        return pantryRepository.save(item);
    }

    @Transactional
    public Optional<PantryItem> updateItem(Long id, PantryItemRequest request) {
        return pantryRepository.findById(id).map(item -> {
            item.setName(request.getName());
            item.setQuantity(request.getQuantity());
            item.setUnit(request.getUnit());
            item.setExpirationDate(request.getExpirationDate());
            return pantryRepository.save(item);
        });
    }

    @Transactional
    public void deleteItem(Long id) {
        pantryRepository.deleteById(id);
    }

    public List<PantryItem> getExpiringItems(int daysAhead) {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(daysAhead);
        return pantryRepository.findByExpirationDateBetween(today, futureDate);
    }

    public List<PantryItem> getExpiredItems() {
        return pantryRepository.findByExpirationDateBefore(LocalDate.now());
    }

    public List<String> getIngredientNames() {
        return pantryRepository.findAll().stream()
                .map(PantryItem::getName)
                .distinct()
                .toList();
    }
}
