package etaskify.repository;

import etaskify.model.entity.Organization;
import etaskify.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select _ from _user _ where _.username = ?1 and _.enabled = true ")
    Optional<User> findByUsernameAndEnabled(String username);

    List<User> findByOrganization(Organization organization);

    List<User> findByOrganizationAndEnabled(Organization organization, boolean enabled);
}