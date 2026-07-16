package de.hochschule.require4testing.service;

import de.hochschule.require4testing.entity.Requirement;
import de.hochschule.require4testing.repository.RequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RequirementService {

    @Autowired
    private RequirementRepository repository;

    public List<Requirement> findAll() { return repository.findAll(); }
    public Requirement findById(Long id) { return repository.findById(id).orElse(null); }
    public Requirement save(Requirement r) {
        Requirement saved = repository.save(r);
        if (saved.getReqKey() == null) {
            saved.setReqKey(String.format("REQ-%03d", saved.getId()));
            saved = repository.save(saved);
        }
        return saved;
    }
    public void deleteById(Long id) { repository.deleteById(id); }
}