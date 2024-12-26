package org.example.erepairshop.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.erepairshop.entities.Billing;
import org.example.erepairshop.entities.PaymentStatus;
import org.example.erepairshop.entities.RepairOrder;
import org.example.erepairshop.services.BillingService;
import org.example.erepairshop.services.RepairOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bills")
public class BillingController {
    private final BillingService bs;
    private final RepairOrderService ros;

    @GetMapping("")
    public String getAllBillings(
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="size", defaultValue = "5")int size,
            @RequestParam(name="name", defaultValue = "") String cname,
            @RequestParam(name="status", defaultValue = "") String status,
            Model m
    ) {
        Page<Billing> bills =  bs.getAllBillings(PageRequest.of(page-1,size));
        m.addAttribute("pages", new int[bills.getTotalPages()]);
        m.addAttribute("current", page);
        m.addAttribute("cname", cname);
        m.addAttribute("status", status);
        m.addAttribute("data", bills);
        return "bills/main";
    }

    @GetMapping("/customer/{id}")
    public String getAllBillingsByCustomerId(
            @PathVariable Long id,
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="size", defaultValue = "5")int size,
            Model m
    ) {
        Page<Billing> bills =  bs.getBillingsByRepairOrderCustomerId(id, PageRequest.of(page-1,size));
        m.addAttribute("pages", new int[bills.getTotalPages()]);
        m.addAttribute("current", page);
        m.addAttribute("data", bills);
        m.addAttribute("customMessage", "Customer #"+id+" bills");
        m.addAttribute("cid", id);
        return "bills/main";
    }

    @GetMapping("/{id}")
    public String getBillingById(
            @PathVariable Long id,
            Model m
    ) {
        Billing bill = bs.getBillingById(id);
        m.addAttribute("bill", bill);
        return "bills/details";
    }

//    @GetMapping("/add/{roId}")
//    public String createBillView(
//            @PathVariable Long roId,
//            Billing bill,
//            Model m
//    ) {
//        try {
//            ros.getRepairOrderById(roId);
//            m.addAttribute("bill", bill);
//            m.addAttribute("roId", roId);
//            return "bills/form";
//        } catch (RuntimeException e) {
//            return "redirect:/repairOrders";
//        }
//    }

    @PostMapping("/add/{roId}")
    public String createBilling(
            @PathVariable Long roId,
            @Valid Billing bill,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "bills/form";
        }
        RepairOrder ro = ros.getRepairOrderById(roId);
        bill.setRepairOrder(ro);
        bill.setTotalAmount(ro.getTotalPrice());

        if (bill.getPaymentStatus().equals(PaymentStatus.PAID))
            bill.setPaymentDate(LocalDateTime.now());

        bs.createBilling(bill);
        return "redirect:/bills";
    }

    @GetMapping("/edit/{id}")
    public String editBillView(
            @RequestParam(name = "id") Long id,
            Model m
    ) {
        Billing bill = bs.getBillingById(id);
        m.addAttribute("bill", bill);
        return "bills/form";
    }

    @PostMapping("/edit")
    public String editBilling(
            @Valid Billing bill,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "bills/form";
        }

        bs.createBilling(bill);
        return "redirect:/bills";
    }

    @GetMapping("/cancel/{id}")
    public String cancelBill(
            @PathVariable Long id
    ) {
        bs.cancelBilling(id);
        return "redirect:/bills";
    }

    @GetMapping("/delete/{id}")
    public String deleteBilling(@PathVariable Long id) {
        bs.deleteBilling(id);
        return "redirect:/bills";
    }
}
