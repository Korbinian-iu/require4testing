package de.hochschule.require4testing.config;

import de.hochschule.require4testing.entity.Role;
import de.hochschule.require4testing.entity.RoleName;
import de.hochschule.require4testing.entity.User;
import de.hochschule.require4testing.repository.RoleRepository;
import de.hochschule.require4testing.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // 1. Alle vier Rollen anlegen, falls noch nicht vorhanden
        for (RoleName rn : RoleName.values()) {
            roleRepository.findByName(rn).orElseGet(() -> roleRepository.save(new Role(rn)));
        }

        // 2. Startbenutzer 'admin' mit allen Rollen anlegen, falls nicht vorhanden
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setEnabled(true);

            Set<Role> roles = new HashSet<>();
            for (RoleName rn : RoleName.values()) {
                roleRepository.findByName(rn).ifPresent(roles::add);
            }
            admin.setRoles(roles);

            userRepository.save(admin);
        }
    }
}