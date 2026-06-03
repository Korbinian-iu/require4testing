package de.hochschule.require4testing.repository;

import de.hochschule.require4testing.entity.TestRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRunRepository extends JpaRepository<TestRun, Long> {
}