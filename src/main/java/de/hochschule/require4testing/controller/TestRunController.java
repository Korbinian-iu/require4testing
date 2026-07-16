package de.hochschule.require4testing.controller;

import de.hochschule.require4testing.entity.TestRun;
import de.hochschule.require4testing.entity.TestRunStatus;
import de.hochschule.require4testing.service.TestRunService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/testruns")
public class TestRunController {

    @Autowired
    private TestRunService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("testruns", service.findAll());
        model.addAttribute("testrun", new TestRun());
        model.addAttribute("statuses", TestRunStatus.values());
        return "testruns/list";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("testrun") TestRun testRun,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("testruns", service.findAll());
            model.addAttribute("statuses", TestRunStatus.values());
            return "testruns/list";
        }
        service.save(testRun);
        return "redirect:/testruns";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam TestRunStatus status) {
        TestRun t = service.findById(id);
        if (t != null) {
            t.setStatus(status);
            service.save(t);
        }
        return "redirect:/testruns";
    }
}