package de.hochschule.require4testing.service;

import de.hochschule.require4testing.entity.TestRun;
import de.hochschule.require4testing.repository.TestRunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestRunService {

    @Autowired
    private TestRunRepository repository;

    public List<TestRun> findAll() { return repository.findAll(); }
    public TestRun findById(Long id) { return repository.findById(id).orElse(null); }
    public TestRun save(TestRun t) { return repository.save(t); }
    public void deleteById(Long id) { repository.deleteById(id); }
}