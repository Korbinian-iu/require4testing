package de.hochschule.require4testing.repository;

import de.hochschule.require4testing.entity.Role;
import de.hochschule.require4testing.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}