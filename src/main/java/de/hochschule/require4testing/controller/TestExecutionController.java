package de.hochschule.require4testing.controller;

import de.hochschule.require4testing.entity.*;
import de.hochschule.require4testing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/executions")
public class TestExecutionController {

    @Autowired private TestExecutionService executionService;
    @Autowired private TestRunService testRunService;
    @Autowired private TestCaseService testCaseService;
    @Autowired private UserService userService;
    @Autowired private StepResultService stepResultService;

    @GetMapping
    public String list(Model model) {
        List<TestExecution> executions = executionService.findAll();
        Map<Long, TestResultStatus> statusByExecution = new HashMap<>();
        for (TestExecution e : executions) {
            statusByExecution.put(e.getId(), stepResultService.aggregatedStatus(e));
        }
        model.addAttribute("executions", executions);
        model.addAttribute("statusByExecution", statusByExecution);
        model.addAttribute("testruns", testRunService.findAll());
        model.addAttribute("testcases", testCaseService.findAll());
        model.addAttribute("testers", userService.findAllTesters());
        return "executions/list";
    }

    @PostMapping
    public String save(@RequestParam Long testRunId,
                       @RequestParam Long testCaseId,
                       @RequestParam Long testerId) {
        TestExecution execution = new TestExecution();
        execution.setTestRun(testRunService.findById(testRunId));
        execution.setTestCase(testCaseService.findById(testCaseId));
        execution.setTester(userService.findById(testerId));
        executionService.save(execution);
        return "redirect:/executions";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        TestExecution execution = executionService.findById(id);
        if (execution == null) return "redirect:/executions";
        model.addAttribute("execution", execution);
        model.addAttribute("steps", execution.getTestCase() != null
                ? execution.getTestCase().getSteps() : Collections.emptyList());
        model.addAttribute("latestByStep", stepResultService.latestByStep(execution));
        model.addAttribute("history", stepResultService.history(execution));
        model.addAttribute("aggregated", stepResultService.aggregatedStatus(execution));
        model.addAttribute("statuses", TestResultStatus.values());
        return "executions/detail";
    }

    @PostMapping("/{id}/steps/{stepId}/result")
    public String recordStep(@PathVariable Long id, @PathVariable Long stepId,
                             @RequestParam TestResultStatus status,
                             @RequestParam(required = false) String comment,
                             Principal principal) {
        TestExecution execution = executionService.findById(id);
        if (execution != null && execution.getTestCase() != null) {
            TestStep step = execution.getTestCase().getSteps().stream()
                    .filter(s -> s.getId().equals(stepId)).findFirst().orElse(null);
            User currentUser = (principal != null) ? userService.findByUsername(principal.getName()) : null;
            if (step != null) {
                stepResultService.record(execution, step, status, comment, currentUser);
            }
        }
        return "redirect:/executions/" + id;
    }
}