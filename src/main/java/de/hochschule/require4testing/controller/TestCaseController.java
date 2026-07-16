package de.hochschule.require4testing.controller;

import de.hochschule.require4testing.entity.*;
import de.hochschule.require4testing.service.RequirementService;
import de.hochschule.require4testing.service.TestCaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        model.addAttribute("priorities", TestCasePriority.values());
        model.addAttribute("types", TestCaseType.values());
        return "testcases/list";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("testcase") TestCase testCase,
                       BindingResult result,
                       @RequestParam(name = "requirementIds", required = false) List<Long> requirementIds,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("testcases", testCaseService.findAll());
            model.addAttribute("requirements", requirementService.findAll());
            model.addAttribute("priorities", TestCasePriority.values());
            model.addAttribute("types", TestCaseType.values());
            return "testcases/list";
        }
        Set<Requirement> reqs = new HashSet<>();
        if (requirementIds != null) {
            for (Long rid : requirementIds) {
                Requirement r = requirementService.findById(rid);
                if (r != null) reqs.add(r);
            }
        }
        testCase.setRequirements(reqs);
        testCaseService.save(testCase);
        return "redirect:/testcases";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        TestCase testCase = testCaseService.findById(id);
        if (testCase == null) return "redirect:/testcases";
        model.addAttribute("testcase", testCase);
        return "testcases/detail";
    }

    @PostMapping("/{id}/steps")
    public String addStep(@PathVariable Long id,
                          @RequestParam String action,
                          @RequestParam(required = false) String expectedResult) {
        TestCase testCase = testCaseService.findById(id);
        if (testCase != null && action != null && !action.isBlank()) {
            int next = testCase.getSteps().stream().mapToInt(TestStep::getStepNumber).max().orElse(0) + 1;
            TestStep step = new TestStep();
            step.setStepNumber(next);
            step.setAction(action);
            step.setExpectedResult(expectedResult);
            step.setTestCase(testCase);
            testCase.getSteps().add(step);
            testCaseService.save(testCase);
        }
        return "redirect:/testcases/" + id;
    }
}