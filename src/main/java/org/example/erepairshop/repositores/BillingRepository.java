package org.example.erepairshop.repositores;

import org.example.erepairshop.entities.Billing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    Page<Billing> findByPaymentStatus(String status, Pageable p);
    Page<Billing> findByRepairOrderCustomerId(Long customerId, Pageable p);
    Page<Billing> findByRepairOrderCustomerNameAndPaymentStatus(String name, String status, Pageable p);
}
