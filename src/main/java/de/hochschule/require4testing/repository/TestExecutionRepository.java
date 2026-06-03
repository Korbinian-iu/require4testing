package de.hochschule.require4testing.repository;

import de.hochschule.require4testing.entity.TestExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestExecutionRepository extends JpaRepository<TestExecution, Long> {
    List<TestExecution> findByTestRunId(Long testRunId);
}
