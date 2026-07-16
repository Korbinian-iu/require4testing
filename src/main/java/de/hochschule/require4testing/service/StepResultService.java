package de.hochschule.require4testing.service;

import de.hochschule.require4testing.entity.*;
import de.hochschule.require4testing.repository.StepResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class StepResultService {

    @Autowired
    private StepResultRepository repository;

    public StepResult record(TestExecution execution, TestStep step,
                             TestResultStatus status, String comment, User testedBy) {
        StepResult r = new StepResult();
        r.setExecution(execution);
        r.setStep(step);
        r.setStatus(status);
        r.setComment(comment);
        r.setTestedBy(testedBy);
        r.setRecordedAt(LocalDateTime.now());
        return repository.save(r);
    }

    public List<StepResult> history(TestExecution execution) {
        return repository.findByExecutionOrderByRecordedAtDesc(execution);
    }

    /** Neuestes Ergebnis je Schritt-ID (Historie ist nach Zeit absteigend sortiert). */
    public Map<Long, StepResult> latestByStep(TestExecution execution) {
        Map<Long, StepResult> latest = new HashMap<>();
        for (StepResult r : history(execution)) {
            if (r.getStep() != null) latest.putIfAbsent(r.getStep().getId(), r);
        }
        return latest;
    }

    /** Aggregierter Status des Testfalls aus den neuesten Schrittergebnissen. */
    public TestResultStatus aggregatedStatus(TestExecution execution) {
        List<TestStep> steps = (execution.getTestCase() != null)
                ? execution.getTestCase().getSteps() : Collections.emptyList();
        if (steps.isEmpty()) return TestResultStatus.NICHT_AUSGEFUEHRT;

        Map<Long, StepResult> latest = latestByStep(execution);
        boolean anyFailed = false, anyBlocked = false, anyRetest = false, allPassed = true, anyRecorded = false;

        for (TestStep step : steps) {
            StepResult r = latest.get(step.getId());
            if (r == null || r.getStatus() == null) { allPassed = false; continue; }
            anyRecorded = true;
            switch (r.getStatus()) {
                case FEHLGESCHLAGEN -> { anyFailed = true; allPassed = false; }
                case BLOCKIERT -> { anyBlocked = true; allPassed = false; }
                case ERNEUT_ZU_TESTEN -> { anyRetest = true; allPassed = false; }
                case BESTANDEN -> { }
                case UEBERSPRUNGEN, NICHT_AUSGEFUEHRT -> allPassed = false;
            }
        }
        if (!anyRecorded) return TestResultStatus.NICHT_AUSGEFUEHRT;
        if (anyFailed) return TestResultStatus.FEHLGESCHLAGEN;
        if (anyBlocked) return TestResultStatus.BLOCKIERT;
        if (anyRetest) return TestResultStatus.ERNEUT_ZU_TESTEN;
        if (allPassed) return TestResultStatus.BESTANDEN;
        return TestResultStatus.NICHT_AUSGEFUEHRT;
    }
}