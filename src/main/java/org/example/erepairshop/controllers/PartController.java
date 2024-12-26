package org.example.erepairshop.controllers;

import lombok.RequiredArgsConstructor;
import org.example.erepairshop.entities.Part;
import org.example.erepairshop.services.PartService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/parts")
public class PartController {
    private final PartService ps;

    @GetMapping("")
    public String getAllPartServices(
            @RequestParam(name="page", defaultValue = "1") int page,
            @RequestParam(name="size", defaultValue = "5")int size,
            Model m
    ) {
        Page<Part> parts = ps.getAllParts(PageRequest.of(page-1, size));
        m.addAttribute("data", parts);
        m.addAttribute("pages", new int[parts.getTotalPages()]);
        m.addAttribute("current", page);
        return "parts/main";
    }

    @GetMapping("/{id}")
    public String getPartById(
            @PathVariable Long id,
            Model m
    ) {
        Part part = ps.getPartById(id);
        m.addAttribute("part", part);
        return "parts/details";
    }

    @GetMapping("/add")
    public String createPartView(
            Part part,
            Model m
    ) {
        m.addAttribute("part", part);
        return "parts/form";
    }

    @PostMapping("/add")
    public String createPart(
            @Validated Part part,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "parts/form";
        }

        ps.createPart(part);
        return "redirect:/parts";
    }

    @GetMapping("/edit/{id}")
    public String editPartView(
            @PathVariable Long id,
            Model m
    ) {
        Part part = ps.getPartById(id);
        m.addAttribute("part", part);
        return "parts/form";
    }

    @PostMapping("/edit/{id}")
    public String editPart(
            @PathVariable Long id,
            @Validated Part part,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "parts/form";
        }

        ps.updatePart(id, part);
        return "redirect:/parts";
    }

    @GetMapping("/delete/{id}")
    public String deletePart(@PathVariable Long id) {
        ps.deletePart(id);
        return "redirect:/parts";
    }
}

