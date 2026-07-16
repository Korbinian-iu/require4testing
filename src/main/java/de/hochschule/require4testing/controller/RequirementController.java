package de.hochschule.require4testing.controller;

import de.hochschule.require4testing.entity.MoSCoWPriority;
import de.hochschule.require4testing.entity.Requirement;
import de.hochschule.require4testing.entity.RequirementStatus;
import de.hochschule.require4testing.service.RequirementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        model.addAttribute("priorities", MoSCoWPriority.values());
        model.addAttribute("statuses", RequirementStatus.values());
        return "requirements/list";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("requirement") Requirement requirement,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("requirements", service.findAll());
            model.addAttribute("priorities", MoSCoWPriority.values());
            model.addAttribute("statuses", RequirementStatus.values());
            return "requirements/list";
        }
        service.save(requirement);
        return "redirect:/requirements";
    }
}