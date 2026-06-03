package de.hochschule.require4testing.repository;

import de.hochschule.require4testing.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    List<TestCase> findByRequirementId(Long requirementId);
}