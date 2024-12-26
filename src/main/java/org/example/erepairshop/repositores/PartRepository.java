package org.example.erepairshop.repositores;

import org.example.erepairshop.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByNameContainingIgnoreCase(String name);
    List<Part> findByStockQuantityLessThan(int threshold);

    @Query("SELECT p FROM Part p WHERE p.stockQuantity = 0")
    List<Part> findOutOfStockParts();
}

