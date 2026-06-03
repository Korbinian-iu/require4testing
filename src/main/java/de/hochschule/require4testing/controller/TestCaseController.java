package de.hochschule.require4testing.controller;

import de.hochschule.require4testing.entity.TestCase;
import de.hochschule.require4testing.service.RequirementService;
import de.hochschule.require4testing.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/testcases")
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private RequirementService requirementService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("testcases", testCaseService.findAll());
        model.addAttribute("testcase", new TestCase());
        model.addAttribute("requirements", requirementService.findAll());
        return "testcases/list";
    }

    @PostMapping
    public String save(@ModelAttribute TestCase testCase,
                       @RequestParam Long requirementId) {
        testCase.setRequirement(requirementService.findById(requirementId));
        testCaseService.save(testCase);
        return "redirect:/testcases";
    }
}
