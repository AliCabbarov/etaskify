package etaskify.repository;

import etaskify.model.entity.Organization;
import etaskify.model.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByOrganization(Organization organization, Pageable pageable);
}