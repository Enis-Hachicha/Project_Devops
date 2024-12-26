package org.example.erepairshop.services;

import lombok.RequiredArgsConstructor;
import org.example.erepairshop.entities.Technician;
import org.example.erepairshop.repositores.TechnicianRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class TechnicianService {
    private final TechnicianRepository technicianRepository;

    public Technician createTechnician(Technician technician) {
        return technicianRepository.save(technician);
    }

    public Technician getTechnicianById(Long id) {
        return technicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technician not found with id: " + id));
    }

    public List<Technician> getAllTechnicians() {
        return technicianRepository.findAll();
    }

    public Page<Technician> getAllTechniciansPaginated(String name, Pageable p) {
        return technicianRepository.findByNameContainingIgnoreCase(name, p);
    }

    public List<Technician> getTechniciansByName(String name) {
        return technicianRepository.findByNameContainingIgnoreCase(name);
    }

    public Technician updateTechnician(Long id, Technician technicianDetails) {
        Technician technician = getTechnicianById(id);
        technician.setName(technicianDetails.getName());
        technician.setSpecialization(technicianDetails.getSpecialization());
        return technicianRepository.save(technician);
    }

    public void deleteTechnician(Long id) {
        technicianRepository.deleteById(id);
    }
}

