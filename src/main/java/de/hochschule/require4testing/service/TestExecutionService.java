package de.hochschule.require4testing.service;

import de.hochschule.require4testing.entity.TestExecution;
import de.hochschule.require4testing.repository.TestExecutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestExecutionService {

    @Autowired
    private TestExecutionRepository repository;

    public List<TestExecution> findAll() { return repository.findAll(); }
    public TestExecution findById(Long id) { return repository.findById(id).orElse(null); }
    public TestExecution save(TestExecution t) { return repository.save(t); }
    public void deleteById(Long id) { repository.deleteById(id); }
    public List<TestExecution> findByTestRunId(Long testRunId) { return repository.findByTestRunId(testRunId); }
    public java.util.List<TestExecution> findByTesterId(Long testerId) {
        return repository.findByTester_Id(testerId);
    }
}