package org.example.erepairshop.controllers;

import lombok.RequiredArgsConstructor;
import org.example.erepairshop.entities.Technician;
import org.example.erepairshop.entities.Technician;
import org.example.erepairshop.services.TechnicianService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/technicians")
public class TechnicianController {
    private final TechnicianService ts;

    @GetMapping("")
    public String getAllTechnicianServices(
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="size", defaultValue = "5")int size,
            @RequestParam(name="name", defaultValue = "") String name,
            Model m
    ) {
        Page<Technician> technicians = ts.getAllTechniciansPaginated(name, PageRequest.of(page-1, size));
        m.addAttribute("data", technicians);
        m.addAttribute("name", name);
        m.addAttribute("pages", new int[technicians.getTotalPages()]);
        m.addAttribute("current", page);
        return "technicians/main";
    }

    @GetMapping("/{id}")
    public String getTechnicianById(
            @PathVariable Long id,
            Model m
    ) {
        Technician technician = ts.getTechnicianById(id);
        m.addAttribute("technician", technician);
        return "technicians/details";
    }

    @GetMapping("/add")
    public String createTechnicianView(
            Technician technician,
            Model m
    ) {
        m.addAttribute("technician", technician);
        return "technicians/form";
    }

    @PostMapping("/add")
    public String createTechnician(
            @Validated Technician tachnician,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "technicians/form";
        }

        ts.createTechnician(tachnician);
        return "redirect:/technicians";
    }

    @GetMapping("/edit/{id}")
    public String editTechnicianView(
            @PathVariable Long id,
            Model m
    ) {
        Technician technician = ts.getTechnicianById(id);
        m.addAttribute("technician", technician);
        return "technicians/form";
    }

    @PostMapping("/edit/{id}")
    public String editTechnician(
            @PathVariable Long id,
            @Validated Technician technician,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "technicians/form";
        }

        ts.updateTechnician(id, technician);
        return "redirect:/technicians";
    }

    @GetMapping("/delete/{id}")
    public String deleteTechnician(@PathVariable Long id) {
        ts.deleteTechnician(id);
        return "redirect:/technicians";
    }
}

