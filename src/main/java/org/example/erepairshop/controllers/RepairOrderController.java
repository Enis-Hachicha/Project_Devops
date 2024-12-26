package org.example.erepairshop.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.erepairshop.entities.Customer;
import org.example.erepairshop.entities.Device;
import org.example.erepairshop.entities.RepairOrder;
import org.example.erepairshop.services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/repairOrders")
public class RepairOrderController {
    private final RepairOrderService ros;
    private final DeviceService ds;
    private final TechnicianService ts;
    private final PartService ps;

    @GetMapping("")
    public String getAllRepairOrders(
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="size", defaultValue = "5")int size,
            @RequestParam(name="cname", defaultValue = "")String cname,
            Model m
    ) {
        // TODO add filtering to repository method
        Page<RepairOrder> repairOrders = ros.getAllRepairOrdersCname(cname, PageRequest.of(page-1, size));
        m.addAttribute("data", repairOrders);
        m.addAttribute("cname", cname);
        m.addAttribute("pages", new int[repairOrders.getTotalPages()]);
        m.addAttribute("current", page);
        return "repairOrders/main";
    }

    @GetMapping("/customer/{id}")
    public String getAllRepairOrdersByCustomerId(
            @PathVariable Long id,
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="size", defaultValue = "5")int size,
            Model m
    ) {
        Page<RepairOrder> repairOrders = ros.getRepairOrdersByCustomerId(id, PageRequest.of(page-1, size));
        m.addAttribute("data", repairOrders);
        m.addAttribute("pages", new int[repairOrders.getTotalPages()]);
        m.addAttribute("current", page);
        m.addAttribute("customMessage", "Customer #"+id+" Repair Orders");
        m.addAttribute("cid", id);
        return "repairOrders/main";
    }

    @GetMapping("/{id}")
    public String getRepairOrderById(
            @PathVariable Long id,
            Model m
    ) {
        RepairOrder repairOrder = ros.getRepairOrderById(id);
        m.addAttribute("repairOrder", repairOrder);
        return "repairOrders/details";
    }

    @GetMapping("/add/device/{deviceId}")
    public String createRepairOrderViewCustomerPopulated(
            @PathVariable Long deviceId,
            RepairOrder repairOrder,
            Model m
    ) {
        try {
            ds.getDeviceById(deviceId);

            m.addAttribute("repairOrder", repairOrder);
            m.addAttribute("technicians", ts.getAllTechnicians());
            m.addAttribute("parts", ps.getAllPartsList());
            m.addAttribute("deviceId", deviceId);

            return "repairOrders/form";
        } catch (RuntimeException e) {
            m.addAttribute("error", "Device with ID: "+deviceId+" not found");
            return "repairOrders/main";
        }
    }

    @PostMapping("/add/device/{deviceId}")
    public String createRepairOrderViewDevicePopulated(
            @PathVariable Long deviceId,
            @Valid @ModelAttribute RepairOrder repairOrder,
            BindingResult result,
            Model m
    ) {
        if (result.hasErrors()) {
            m.addAttribute("error", result.getAllErrors());
            return "repairOrders/form";
        }

        Device device = ds.getDeviceById(deviceId);
        Customer customer = device.getOwner();
        repairOrder.setCustomer(customer);
        repairOrder.setDevice(device);

        ros.createRepairOrder(repairOrder);
        return "redirect:/repairOrders";
    }

    @GetMapping("/edit/{id}")
    public String updateRepairOrderView(
            @PathVariable Long id,
            Model m
    ) {
        RepairOrder repairOrder = ros.getRepairOrderById(id);
        m.addAttribute("repairOrder", repairOrder);
        return  "repairOrders/form";
    }

    @PatchMapping("/edit/{id}")
    public String updateRepairOrder(
            @PathVariable Long id,
            @Valid @ModelAttribute RepairOrder repairOrder,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "repairOrders/form";
        }

        ros.updateRepairOrder(id, repairOrder);
        return  "redirect:/repairOrders";
    }

    @GetMapping("/delete/{id}")
    public String deleteRepairOrder(
            @PathVariable Long id
    ) {
        ros.deleteRepairOrder(id);
        return "redirect:/repairOrders";
    }
}

