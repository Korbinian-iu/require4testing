package de.hochschule.require4testing.controller;

import de.hochschule.require4testing.entity.TestRun;
import de.hochschule.require4testing.service.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "testruns/list";
    }

    @PostMapping
    public String save(@ModelAttribute TestRun testRun) {
        service.save(testRun);
        return "redirect:/testruns";
    }
}