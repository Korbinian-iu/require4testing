package de.hochschule.require4testing.controller;

import de.hochschule.require4testing.dto.TestRunSummary;
import de.hochschule.require4testing.entity.*;
import de.hochschule.require4testing.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class DashboardController {

    @Autowired private TestExecutionService executionService;
    @Autowired private TestRunService testRunService;
    @Autowired private StepResultService stepResultService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<TestExecution> all = executionService.findAll();
        Map<Long, List<TestExecution>> byRun = new HashMap<>();
        for (TestExecution e : all) {
            if (e.getTestRun() != null) {
                byRun.computeIfAbsent(e.getTestRun().getId(), k -> new ArrayList<>()).add(e);
            }
        }

        List<TestRunSummary> summaries = new ArrayList<>();
        for (TestRun run : testRunService.findAll()) {
            List<TestExecution> execs = byRun.getOrDefault(run.getId(), new ArrayList<>());
            long passed=0, failed=0, blocked=0, skipped=0, retest=0, notExec=0;
            for (TestExecution e : execs) {
                switch (stepResultService.aggregatedStatus(e)) {
                    case BESTANDEN -> passed++;
                    case FEHLGESCHLAGEN -> failed++;
                    case BLOCKIERT -> blocked++;
                    case UEBERSPRUNGEN -> skipped++;
                    case ERNEUT_ZU_TESTEN -> retest++;
                    case NICHT_AUSGEFUEHRT -> notExec++;
                }
            }
            long total = execs.size();
            long executed = total - notExec;

            TestRunSummary s = new TestRunSummary();
            s.setRunName(run.getName());
            s.setRunStatus(run.getStatus() != null ? run.getStatus().name() : "");
            s.setTotal(total);
            s.setPassed(passed);
            s.setFailed(failed);
            s.setBlocked(blocked);
            s.setSkipped(skipped);
            s.setRetest(retest);
            s.setNotExecuted(notExec);
            s.setExecuted(executed);
            s.setPassRatePercent(total > 0 ? (int) Math.round(passed * 100.0 / total) : 0);
            s.setProgressPercent(total > 0 ? (int) Math.round(executed * 100.0 / total) : 0);
            summaries.add(s);
        }
        model.addAttribute("summaries", summaries);
        return "dashboard";
    }
}