package org.example.erepairshop.repositores;

import org.example.erepairshop.entities.Technician;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    Page<Technician> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Technician> findByNameContainingIgnoreCase(String name);
}

