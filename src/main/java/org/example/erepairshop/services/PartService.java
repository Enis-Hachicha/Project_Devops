package org.example.erepairshop.services;

import lombok.RequiredArgsConstructor;
import org.example.erepairshop.entities.Part;
import org.example.erepairshop.repositores.PartRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PartService {
    private final PartRepository partRepository;

    public Part createPart(Part part) {
        return partRepository.save(part);
    }

    public Part getPartById(Long id) {
        return partRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Part not found with id: " + id));
    }

    public Page<Part> getAllParts(Pageable p) {
        return partRepository.findAll(p);
    }

    public List<Part> getAllPartsList() {
        return partRepository.findAll();
    }


    public List<Part> getPartsByName(String name) {
        return partRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Part> getPartsWithLowStock(int threshold) {
        return partRepository.findByStockQuantityLessThan(threshold);
    }

    public List<Part> getOutOfStockParts() {
        return partRepository.findOutOfStockParts();
    }

    public Part updatePart(Long id, Part partDetails) {
        Part part = getPartById(id);
        part.setName(partDetails.getName());
        part.setDescription(partDetails.getDescription());
        part.setPrice(partDetails.getPrice());
        part.setStockQuantity(partDetails.getStockQuantity());
        return partRepository.save(part);
    }

    public void deletePart(Long id) {
        partRepository.deleteById(id);
    }
}

