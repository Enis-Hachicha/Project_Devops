package org.example.erepairshop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.erepairshop.entities.Customer;
import org.example.erepairshop.services.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService cs;

    @GetMapping("")
    public String getAllPaginatedCustomersOrQueryByName(
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="size", defaultValue = "5")int size,
            @RequestParam(name="name", defaultValue = "") String name,
            Model m
    ) {
        Page<Customer> allCustomers =  cs.getCustomersByName(name, PageRequest.of(page-1,size));

        m.addAttribute("data", allCustomers.getContent());
        m.addAttribute("name", name);
        m.addAttribute("pages", new int[allCustomers.getTotalPages()]);
        m.addAttribute("current", page);

        return "customers/main";
    }

    @GetMapping("/{id}")
    public String getCustomerById(@PathVariable Long id, Model m) {
        Customer customer = cs.getCustomerById(id);
        m.addAttribute("customer", customer);
        return "customers/details";
    }

    @GetMapping("/add")
    public String createCustomerView(
            Customer customer,
            Model m
    ) {
        m.addAttribute("customer", customer);
        return "customers/form";
    }

    @PostMapping("/add")
    public String createCustomer(
            @Valid @ModelAttribute Customer customer,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "customers/form";
        }

        cs.createCustomer(customer);
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String updateCustomerView(
            @PathVariable Long id,
            Model m
    ) {
        Customer customer = cs.getCustomerById(id);
        m.addAttribute("customer", customer);
        return "customers/form";
    }

    @PostMapping("/edit/{id}")
    public String updateCustomer(
            @PathVariable Long id,
            @Valid @ModelAttribute Customer customer,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "customers/form";
        }

        cs.updateCustomer(id, customer);
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        cs.deleteCustomer(id);
        return "redirect:/customers";
    }
}
