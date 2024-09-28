package etaskify.repository;

import etaskify.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(String roleUser);

    Optional<Role> findByName(String name);
}