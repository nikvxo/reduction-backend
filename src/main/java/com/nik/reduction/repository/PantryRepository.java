package com.nik.reduction.repository;

import com.nik.reduction.model.PantryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PantryRepository extends JpaRepository<PantryItem, Long> {

    List<PantryItem> findByExpirationDateBefore(LocalDate date);

    List<PantryItem> findByExpirationDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT p FROM PantryItem p ORDER BY p.expirationDate ASC NULLS LAST")
    List<PantryItem> findAllOrderByExpiration();
}
