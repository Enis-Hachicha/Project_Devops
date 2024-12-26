package org.example.erepairshop.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.erepairshop.entities.Billing;
import org.example.erepairshop.entities.PaymentStatus;
import org.example.erepairshop.entities.RepairOrder;
import org.example.erepairshop.repositores.BillingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BillingService {
    private final BillingRepository br;

    public Page<Billing> getAllBillings(Pageable p) {
        return br.findAll(p);
    }

    public Billing getBillingById(Long id) {
        return br.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing not found with id: " + id));
    }

    public Page<Billing> getBillingsByPaymentStatus(String status, Pageable p) {
        return br.findByPaymentStatus(status, p);
    }

    public Page<Billing> getBillingsByRepairOrderCustomerId(Long customerId, Pageable p) {
        return br.findByRepairOrderCustomerId(customerId, p);
    }

    public Page<Billing> getBillingsByCustomerNameAndStatus(String cname, String status, Pageable p) {
        return br.findByRepairOrderCustomerNameAndPaymentStatus(cname, status, p);
    }

    @Transactional
    public Billing createBillingWithRepairOrderAndAmount(RepairOrder repairOrder, BigDecimal amount) {
        Billing billing = new Billing();
        billing.setRepairOrder(repairOrder);
        billing.setTotalAmount(amount);
        billing.setPaymentStatus(PaymentStatus.PENDING);
        return br.save(billing);
    }

    @Transactional
    public Billing createBilling(Billing bill) {
        return br.save(bill);
    }

    @Transactional
    public Billing updateBillingAmount(Long id, BigDecimal amount) {
        Billing billing = getBillingById(id);
        billing.setTotalAmount(amount);
        return br.save(billing);
    }

    @Transactional
    public Billing updateBillingStatus(Long id, PaymentStatus status) {
        Billing billing = getBillingById(id);
        billing.setPaymentStatus(status);
        if (status.equals(PaymentStatus.PAID))
            billing.setPaymentDate(LocalDateTime.now());
        return br.save(billing);
    }

    @Transactional
    public void deleteBilling(Long id) {
        if (!br.existsById(id)) {
            throw new RuntimeException("Billing not found with id: " + id);
        }
        br.deleteById(id);
    }

    @Transactional
    public Billing cancelBilling(Long id) {
        Billing billing = getBillingById(id);

        if (PaymentStatus.PAID.equals(billing.getPaymentStatus())) {
            throw new IllegalStateException("Cannot cancel a paid billing");
        }

        billing.setPaymentStatus(PaymentStatus.CANCELED);
        return br.save(billing);
    }

    public boolean hasPendingBillings(Long customerId, Pageable p) {
        return getBillingsByRepairOrderCustomerId(customerId, p).stream()
                .anyMatch(billing -> PaymentStatus.PENDING.equals(billing.getPaymentStatus()));
    }
}
