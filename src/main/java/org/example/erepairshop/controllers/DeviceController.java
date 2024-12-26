package org.example.erepairshop.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.erepairshop.entities.Customer;
import org.example.erepairshop.entities.Device;
import org.example.erepairshop.services.CustomerService;
import org.example.erepairshop.services.DeviceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService ds;
    private final CustomerService cs;

    @GetMapping("")
    public String getAllDevices(
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="size", defaultValue = "5")int size,
            Model m
    ) {
        Page<Device> devices= ds.getAllDevices(PageRequest.of(page-1, size));
        m.addAttribute("data", devices);
        m.addAttribute("pages", new int[devices.getTotalPages()]);
        m.addAttribute("current", page);
        return "devices/main";
    }

    @GetMapping("/{id}")
    public String getDeviceById(@PathVariable Long id, Model m) {
        Device device = ds.getDeviceById(id);
        m.addAttribute("device", device);
        return "devices/details";
    }

    @GetMapping("/add/customer/{id}")
    public String createDeviceView(
            @PathVariable Long id,
            Device device,
            Model m
    ) {
        m.addAttribute("cid", id);
        m.addAttribute("device", device);
        return "devices/form";
    }

    @PostMapping("/add/{customerId}")
    public String createDevice(
            @PathVariable Long customerId,
            @Valid @ModelAttribute Device device,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "devices/form";
        }
        Customer customer = cs.getCustomerById(customerId);
        device.setOwner(customer);
        ds.createDevice(device);
        return "redirect:/devices";
    }

    @GetMapping("/edit/{id}")
    public String updateDeviceView(
            @PathVariable Long id,
            Model m
    ) {
        Device device = ds.getDeviceById(id);
        m.addAttribute("device", device);
        return "devices/form";
    }

    @PostMapping("/edit/{id}")
    public String updateDevice(
            @PathVariable Long id,
            @Valid @ModelAttribute Device device,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "devices/form";
        }

        ds.updateDevice(id, device);
        return "redirect:/devices";
    }
    @GetMapping("/delete/{id}")
    public String deleteDevice(@PathVariable Long id) {
        ds.deleteDevice(id);
        return "redirect:/devices";
    }

    @GetMapping("/owner/{id}")
    public String getDeviceByOwnerId(
            @PathVariable Long id,
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="size", defaultValue = "5")int size,
            Model m
    ) {
        Page<Device> devices = ds.getDevicesByOwnerId(id, PageRequest.of(page-1, size));
        m.addAttribute("data", devices);
        m.addAttribute("pages", new int[devices.getTotalPages()]);
        m.addAttribute("current", page);
        m.addAttribute("customMessage", "Customer #"+id+" devices");
        return "devices/main";
    }

//    @GetMapping("/serialNumber/{id}")
//    public Device getDeviceBySerialNumber(@PathVariable String sn) {
//        return ds.getDeviceBySerialNumber(sn);
//    }
//
//    @PostMapping
//    public Device createDevice(@RequestBody Device customer) {
//        return ds.createDevice(customer);
//    }
//
//    @PatchMapping("/{id}")
//    public Device updateDevice(@PathVariable Long id, @RequestBody Device customer) {
//        return  ds.updateDevice(id, Devices);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteDevice(@PathVariable Long id) {
//        ds.deleteDevice(id);
//    }
}
