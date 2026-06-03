package de.hochschule.require4testing.controller;

import de.hochschule.require4testing.entity.Requirement;
import de.hochschule.require4testing.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/requirements")
public class RequirementController {

    @Autowired
    private RequirementService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("requirements", service.findAll());
        model.addAttribute("requirement", new Requirement());
        return "requirements/list";
    }

    @PostMapping
    public String save(@ModelAttribute Requirement requirement) {
        service.save(requirement);
        return "redirect:/requirements";
    }
}
