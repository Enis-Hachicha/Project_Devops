package org.example.erepairshop.services;

import lombok.RequiredArgsConstructor;
import org.example.erepairshop.entities.Customer;
import org.example.erepairshop.repositores.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository cr;



    public Customer createCustomer(Customer customer) {
        return cr.save(customer);
    }


    public Customer getCustomerById(Long id) {
        return cr.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }


    public Page<Customer> getCustomersByName(String name, Pageable p) {
        return cr.findByNameContainingIgnoreCase(name, p);
    }


    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());
        return cr.save(customer);
    }


    public void deleteCustomer(Long id) {
        cr.deleteById(id);
    }
}
