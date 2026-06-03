package de.hochschule.require4testing.service;

import de.hochschule.require4testing.entity.TestCase;
import de.hochschule.require4testing.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestCaseService {

    @Autowired
    private TestCaseRepository repository;

    public List<TestCase> findAll() { return repository.findAll(); }
    public TestCase findById(Long id) { return repository.findById(id).orElse(null); }
    public TestCase save(TestCase t) { return repository.save(t); }
    public void deleteById(Long id) { repository.deleteById(id); }
}