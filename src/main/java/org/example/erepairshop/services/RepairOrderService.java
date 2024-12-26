package org.example.erepairshop.services;

import lombok.RequiredArgsConstructor;
import org.example.erepairshop.entities.PaymentStatus;
import org.example.erepairshop.entities.RepairOrder;
import org.example.erepairshop.repositores.RepairOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class RepairOrderService {
    private final RepairOrderRepository repairOrderRepository;

    public RepairOrder createRepairOrder(RepairOrder repairOrder) {
        return repairOrderRepository.save(repairOrder);
    }

    public RepairOrder getRepairOrderById(Long id) {
        return repairOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repair order not found with id: " + id));
    }

    public Page<RepairOrder> getAllRepairOrdersCname(String cname,Pageable p) {
        return repairOrderRepository.findByCustomerNameContains(cname, p);
    }

    public Page<RepairOrder> getAllRepairOrders(Pageable p) {
        return repairOrderRepository.findAll(p);
    }

    public Page<RepairOrder> getRepairOrdersByCustomerId(Long customerId, Pageable p) {
        return repairOrderRepository.findByCustomerId(customerId, p);
    }

    public List<RepairOrder> getRepairOrdersByTechnicianId(Long technicianId) {
        return repairOrderRepository.findByTechnicianId(technicianId);
    }

    public List<RepairOrder> getRepairOrdersByStatus(PaymentStatus status) {
        return repairOrderRepository.findByStatus(status);
    }

    public List<RepairOrder> getRepairOrdersByDateRange(LocalDateTime start, LocalDateTime end) {
        return repairOrderRepository.findByCreatedAtBetween(start, end);
    }

    public List<RepairOrder> getRepairOrdersByDeviceSerialNumber(String serialNumber) {
        return repairOrderRepository.findByDeviceSerialNumber(serialNumber);
    }

    public RepairOrder updateRepairOrder(Long id, RepairOrder repairOrderDetails) {
        RepairOrder repairOrder = getRepairOrderById(id);
        repairOrder.setDescription(repairOrderDetails.getDescription());
        repairOrder.setStatus(repairOrderDetails.getStatus());
        repairOrder.setTechnician(repairOrderDetails.getTechnician());
        return repairOrderRepository.save(repairOrder);
    }

    public void deleteRepairOrder(Long id) {
        repairOrderRepository.deleteById(id);
    }
}

