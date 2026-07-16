package de.hochschule.require4testing.service;

import de.hochschule.require4testing.entity.RoleName;
import de.hochschule.require4testing.entity.User;
import de.hochschule.require4testing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllTesters() { return userRepository.findByRoles_Name(RoleName.TESTER); }
    public User findById(Long id) { return userRepository.findById(id).orElse(null); }
    public List<User> findAll() { return userRepository.findAll(); }
}