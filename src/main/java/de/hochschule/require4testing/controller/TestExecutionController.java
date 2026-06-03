package de.hochschule.require4testing.controller;

import de.hochschule.require4testing.entity.TestExecution;
import de.hochschule.require4testing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/executions")
public class TestExecutionController {

    @Autowired
    private TestExecutionService executionService;

    @Autowired
    private TestRunService testRunService;

    @Autowired
    private TestCaseService testCaseService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("executions", executionService.findAll());
        model.addAttribute("testruns", testRunService.findAll());
        model.addAttribute("testcases", testCaseService.findAll());
        model.addAttribute("execution", new TestExecution());
        model.addAttribute("results", TestExecution.Result.values());
        return "executions/list";
    }

    @PostMapping
    public String save(@ModelAttribute TestExecution execution,
                       @RequestParam Long testRunId,
                       @RequestParam Long testCaseId) {
        execution.setTestRun(testRunService.findById(testRunId));
        execution.setTestCase(testCaseService.findById(testCaseId));
        executionService.save(execution);
        return "redirect:/executions";
    }

    @PostMapping("/{id}/result")
    public String updateResult(@PathVariable Long id,
                               @RequestParam TestExecution.Result result) {
        TestExecution e = executionService.findById(id);
        e.setResult(result);
        executionService.save(e);
        return "redirect:/executions";
    }
}