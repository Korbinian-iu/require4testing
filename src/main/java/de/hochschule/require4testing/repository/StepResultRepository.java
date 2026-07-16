package de.hochschule.require4testing.repository;

import de.hochschule.require4testing.entity.StepResult;
import de.hochschule.require4testing.entity.TestExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StepResultRepository extends JpaRepository<StepResult, Long> {
    List<StepResult> findByExecutionOrderByRecordedAtDesc(TestExecution execution);
}