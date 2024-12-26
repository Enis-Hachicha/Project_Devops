package org.example.erepairshop.repositores;

import org.example.erepairshop.entities.PaymentStatus;
import org.example.erepairshop.entities.RepairOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RepairOrderRepository extends JpaRepository<RepairOrder, Long> {
    Page<RepairOrder> findByCustomerId(Long customerId, Pageable p);
    List<RepairOrder> findByTechnicianId(Long technicianId);
    List<RepairOrder> findByStatus(PaymentStatus status);
    List<RepairOrder> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    @Query("SELECT ro FROM RepairOrder ro WHERE ro.device.serialNumber = :serialNumber")
    List<RepairOrder> findByDeviceSerialNumber(String serialNumber);
    Page<RepairOrder> findByCustomerNameContains(String customerName, Pageable pageable);
}

